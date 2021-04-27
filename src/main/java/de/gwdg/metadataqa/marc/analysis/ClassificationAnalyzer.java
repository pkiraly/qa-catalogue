package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.ClassificationSchemes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.Utils.count;

public class ClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    ClassificationAnalyzer.class.getCanonicalName()
  );
  private static ClassificationSchemes classificationSchemes =
    ClassificationSchemes.getInstance();
  private static Pattern NUMERIC = Pattern.compile("^\\d");

  private final ClassificationStatistics statistics;
  private MarcRecord marcRecord;
  private List<Schema> schemasInRecord;

  private static final List<String> fieldsWithIndicator1AndSubfield2 = Arrays.asList(
    "052", // Geographic Classification
    "086"  // Government Document Classification Number
  );

  // 055 $2 -- Used only when the second indicator contains value
  // 6 (Other call number assigned by LAC),
  // 7 (Other class number assigned by LAC),
  // 8 (Other call number assigned by the contributing library),
  // 9 (Other class number assigned by the contributing library).
  private static final List<String> fieldsWithIndicator2AndSubfield2 = Arrays.asList(
    "055", // Classification Numbers Assigned in Canada
    "072", // Subject Category Code
    "600", // Subject Added Entry - Personal Name
    "610", // Subject Added Entry - Corporate Name
    "611", // Subject Added Entry - Meeting Name
    "630", // Subject Added Entry - Uniform Title
    "647", // Subject Added Entry - Named Event
    "648", // Subject Added Entry - Chronological Term
    "650", // Subject Added Entry - Topical Term
    "651", // Subject Added Entry - Geographic Name
    "655", // Index Term - Genre/Form
    "656", // Index Term - Occupation
    "657"  // Index Term - Function
  );

  private static final List<String> fieldsWithSubfield2 = Arrays.asList(
    "084", // Other Classificaton Number
    "654", // Subject Added Entry - Faceted Topical Terms
    "658", // Index Term - Curriculum Objective
    "662"  // Subject Added Entry - Hierarchical Place Name
  );

  private static final List<String> fieldsWithoutSource = Arrays.asList(
    "653"  // Index Term - Uncontrolled
  );

  private static final List<FieldWithScheme> fieldsWithScheme = Arrays.asList(
    new FieldWithScheme("080", "Universal Decimal Classification"),
    new FieldWithScheme("082", "Dewey Decimal Classification"),
    new FieldWithScheme("083", "Dewey Decimal Classification"),
    new FieldWithScheme("085", "Dewey Decimal Classification")
    // new FieldWithScheme("086", "Government Document Classification");
  );

  public ClassificationAnalyzer(MarcRecord marcRecord, ClassificationStatistics statistics) {
    this.marcRecord = marcRecord;
    this.statistics = statistics;
  }

  public int process() {
    int total = 0;
    schemasInRecord = new ArrayList<>();

    total = processFieldsWithIndicator1AndSubfield2(total);
    total = processFieldsWithIndicator2AndSubfield2(total);
    total = processFieldsWithSubfield2(total);
    total = processFieldsWithoutSource(total);
    total = processFieldsWithScheme(total);

    increaseCounters(total);

    return total;
  }

  private void increaseCounters(int total) {
    /*
    if (total != schemasInRecord.size())
      logger.severe(String.format("COUNT: total (%d) != schemasInRecord(%d)",
        total, schemasInRecord.size()));
     */
    count((total > 0), statistics.getHasClassifications());
    count(total, statistics.getSchemaHistogram());
    if (!statistics.getFrequencyExamples().containsKey(total))
      statistics.getFrequencyExamples().put(total, marcRecord.getId(true));

    List<String> collocation = getCollocationInRecord();
    if (!collocation.isEmpty())
      count(collocation, statistics.getCollocationHistogram());
  }

  private int processFieldsWithScheme(int total) {
    for (FieldWithScheme fieldWithScheme : fieldsWithScheme) {
      int count = processFieldWithScheme(marcRecord, fieldWithScheme);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithoutSource(int total) {
    for (String tag : fieldsWithoutSource) {
      int count = processFieldWithoutSource(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithSubfield2(int total) {
    for (String tag : fieldsWithSubfield2) {
      int count = processFieldWithSubfield2(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithIndicator2AndSubfield2(int total) {
    for (String tag : fieldsWithIndicator2AndSubfield2) {
      int count = processFieldWithIndicator2AndSubfield2(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithIndicator1AndSubfield2(int total) {
    for (String tag : fieldsWithIndicator1AndSubfield2) {
      int count = processFieldWithIndicator1AndSubfield2(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldWithScheme(MarcRecord marcRecord,
                                     FieldWithScheme fieldEntry) {
    int count = 0;
    final String tag = fieldEntry.getTag();
    if (!marcRecord.hasDatafield(tag))
      return count;

    Map<String[], Integer> fieldStatistics = getFieldInstanceStatistics(tag);
    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      String firstSubfield = null;
      String alt = null;
      for (MarcSubfield subfield : field.getSubfields()) {
        String code = subfield.getCode();
        if (   !code.equals("1")
            && !code.equals("2")
            && !code.equals("6")
            && !code.equals("8")) {
          firstSubfield = "$" + code;
          break;
        } else {
          if (alt == null)
            alt = "$" + code;
        }
      }
      if (firstSubfield != null) {
        String scheme = fieldEntry.getSchemaName();
        Schema currentSchema = new Schema(
          tag, firstSubfield, classificationSchemes.resolve(scheme), scheme);
        schemas.add(currentSchema);
        updateSchemaSubfieldStatistics(field, currentSchema);
        count++;
      } else {
        logger.severe(String.format("undetected subfield in record %s %s", marcRecord.getId(), field.toString()));
      }
    }

    registerSchemas(schemas);

    return count;
  }

  private void registerSchemas(List<Schema> schemas) {
    addSchemasToStatistics(statistics.getInstances(), schemas);

    List<Schema> uniqSchemas = deduplicateSchema(schemas);
    addSchemasToStatistics(statistics.getRecords(), uniqSchemas);
    schemasInRecord.addAll(uniqSchemas);
  }

  private int processFieldWithIndicator1AndSubfield2(MarcRecord marcRecord, String tag) {
    int count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

    // Map<String[], Integer> fieldStatistics = getFieldInstanceStatistics(tag);
    List<Schema> schemas = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafield(tag);
    for (DataField field : fields) {
      String scheme = field.resolveInd1();
      if (scheme.equals("No information provided"))
        continue;

      count++;
      Schema currentSchema = null;
      if (isaReferenceToSubfield2(tag, scheme)) {
        currentSchema = extractSchemaFromSubfield2(tag, schemas, field);
        // } else if (scheme.equals("9")) {
      } else {
        try {
          currentSchema = new Schema(tag, "ind1", classificationSchemes.resolve(scheme), scheme);
        } catch (IllegalArgumentException e) {
          logger.severe(String.format("Invalid scheme in ind1: %s. %s", e.getLocalizedMessage(), field));
          currentSchema = new Schema(tag, "ind1", field.getInd1(), scheme);
        }
        schemas.add(currentSchema);
      }
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return count;
  }

  private int processFieldWithIndicator2AndSubfield2(MarcRecord marcRecord, String tag) {
    int count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

    // Map<String[], Integer> fieldStatistics = getFieldInstanceStatistics(tag);
    List<Schema> schemas = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafield(tag);
    for (DataField field : fields) {
      String scheme = field.resolveInd2();
      Schema currentSchema = null;
      if (isaReferenceToSubfield2(tag, scheme)) {
        currentSchema = extractSchemaFromSubfield2(tag, schemas, field);
        count++;
      } else {
        try {
          currentSchema = new Schema(tag, "ind2", classificationSchemes.resolve(scheme), scheme);
        } catch (IllegalArgumentException e) {
          logger.warning(String.format("Invalid scheme in ind2: %s. %s", e.getLocalizedMessage(), field));
          currentSchema = new Schema(tag, "ind2", field.getInd2(), scheme);
        }
        schemas.add(currentSchema);
        count++;
      }
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return count;
  }

  private int processFieldWithSubfield2(MarcRecord marcRecord, String tag) {
    int count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      Schema currentSchema = extractSchemaFromSubfield2(tag, schemas, field);
      updateSchemaSubfieldStatistics(field, currentSchema);
      count++;
    }

    registerSchemas(schemas);

    return count;
  }

  private int processFieldWithoutSource(MarcRecord marcRecord, String tag) {
    int count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      String abbreviavtion = field.getInd2().equals(" ") ? "#" : field.getInd2();
      Schema currentSchema = new Schema(tag, "ind2", "uncontrolled/" + abbreviavtion, field.resolveInd2());
      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
      count++;
    }

    registerSchemas(schemas);

    return count;
  }

  private Schema extractSchemaFromSubfield2(String tag,
                                            List<Schema> schemas,
                                            DataField field) {
    Schema currentSchema = null;
    List<MarcSubfield> altSchemes = field.getSubfield("2");
    if (altSchemes == null || altSchemes.isEmpty()) {
      currentSchema = new Schema(tag, "$2", "undetectable", "undetectable");
      schemas.add(currentSchema);
    } else {
      for (MarcSubfield altScheme : altSchemes) {
        currentSchema = new Schema(tag, "$2", altScheme.getValue(), altScheme.resolve());
        schemas.add(currentSchema);
      }
    }
    return currentSchema;
  }

  private void updateSchemaSubfieldStatistics(DataField field, Schema currentSchema) {
    if (currentSchema == null)
      return;
    List<String> subfields = orderSubfields(field.getSubfields());

    if (!statistics.getSubfields().containsKey(currentSchema)) {
      statistics.getSubfields().put(currentSchema, new HashMap<List<String>, Integer>());
    }
    Map<List<String>, Integer> subfieldsStatistics =
      statistics.getSubfields().get(currentSchema);
    if (!subfieldsStatistics.containsKey(subfields)) {
      subfieldsStatistics.put(subfields, 1);
    } else {
      subfieldsStatistics.put(subfields, subfieldsStatistics.get(subfields) + 1);
    }
  }

  private List<String> orderSubfields(List<MarcSubfield> originalSubfields) {
    List<String> subfields = new ArrayList<>();
    Set<String> multiFields = new HashSet<>();
    for (MarcSubfield subfield : originalSubfields) {
      String code = subfield.getCode();
      if (!subfields.contains(code))
        subfields.add(code);
      else
        multiFields.add(code);
    }
    if (!multiFields.isEmpty()) {
      for (String code : multiFields)
        subfields.remove(code);
      for (String code : multiFields)
        subfields.add(code + "+");
    }

    List<String> alphabetic = new ArrayList<>();
    List<String> numeric = new ArrayList<>();
    for (String subfield : subfields) {
      if (NUMERIC.matcher(subfield).matches()) {
        numeric.add(subfield);
      } else {
        alphabetic.add(subfield);
      }
    }
    if (!numeric.isEmpty()) {
      Collections.sort(alphabetic);
      Collections.sort(numeric);
      subfields = alphabetic;
      subfields.addAll(numeric);
    } else {
      Collections.sort(subfields);
    }
    return subfields;
  }

  private List<Schema> deduplicateSchema(List<Schema> schemas) {
    Set<Schema> set = new HashSet<Schema>(schemas);
    List<Schema> deduplicated = new ArrayList<Schema>();
    deduplicated.addAll(new HashSet<Schema>(schemas));
    return deduplicated;
  }

  private boolean isaReferenceToSubfield2(String field, String scheme) {
    return (
         (field.equals("055") && isAReferenceFrom055(scheme))
      || scheme.equals("Source specified in subfield $2"));
  }

  private boolean isAReferenceFrom055(String scheme) {
    return (scheme.equals("Other call number assigned by LAC")
      || scheme.equals("Other class number assigned by LAC")
      || scheme.equals("Other call number assigned by the contributing library")
      || scheme.equals("Other class number assigned by the contributing library"));
  }

  private void addSchemasToStatistics(Map<Schema, Integer> fieldStatistics,
                                      List<Schema> schemes) {
    if (!schemes.isEmpty()) {
      for (Schema scheme : schemes) {
        if (!fieldStatistics.containsKey(scheme)) {
          fieldStatistics.put(scheme, 0);
        }
        fieldStatistics.put(scheme, fieldStatistics.get(scheme) + 1);
      }
    }
  }

  private void addSchemesToStatistics(Map<String[], Integer> fieldStatistics,
                                      List<String[]> schemes) {
    if (!schemes.isEmpty()) {
      for (String[] scheme : schemes) {
        if (!fieldStatistics.containsKey(scheme)) {
          fieldStatistics.put(scheme, 0);
          if (!statistics.getFieldInRecords().containsKey(scheme)) {
            statistics.getFieldInRecords().put(scheme, 0);
          }
          statistics.getFieldInRecords().put(
            scheme,
            statistics.getFieldInRecords().get(scheme) + 1
          );
        }
        fieldStatistics.put(scheme, fieldStatistics.get(scheme) + 1);
      }
    }
  }

  private Map<String[], Integer> getFieldInstanceStatistics(String field) {
    if (!statistics.getFieldInstances().containsKey(field)) {
      statistics.getFieldInstances().put(field, new HashMap<String[], Integer>());
    }
    return statistics.getFieldInstances().get(field);
  }

  public List<Schema> getSchemasInRecord() {
    return schemasInRecord;
  }

  public List<String> getCollocationInRecord() {
    List<String> abbreviations = schemasInRecord
      .stream()
      .map(Schema::getAbbreviation)
      .sorted()
      .distinct()
      .collect(Collectors.toList());
    return abbreviations;
  }
}
