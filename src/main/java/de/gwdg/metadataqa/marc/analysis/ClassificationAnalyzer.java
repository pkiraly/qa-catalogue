package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.ClassificationSchemes;
import de.gwdg.metadataqa.marc.utils.pica.PicaVocabularyManager;
import de.gwdg.metadataqa.marc.utils.pica.VocabularyEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.Utils.count;

public class ClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    ClassificationAnalyzer.class.getCanonicalName()
  );
  private static final ClassificationSchemes classificationSchemes =
    ClassificationSchemes.getInstance();
  private static final Pattern NUMERIC = Pattern.compile("^\\d");
  public static final String DEWEY_DECIMAL_CLASSIFICATION = "Dewey Decimal Classification";
  private static PicaVocabularyManager manager = null;

  private final ClassificationStatistics statistics;
  private ClassificationParameters parameters = null;
  private BibliographicRecord marcRecord;
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

  private static final List<FieldWithScheme> MARC21_FIELD_WITH_SCHEMES = Arrays.asList(
    new FieldWithScheme("080", "Universal Decimal Classification"),
    new FieldWithScheme("082", DEWEY_DECIMAL_CLASSIFICATION),
    new FieldWithScheme("083", DEWEY_DECIMAL_CLASSIFICATION),
    new FieldWithScheme("085", DEWEY_DECIMAL_CLASSIFICATION)
    // new FieldWithScheme("086", "Government Document Classification");
  );

  private static final List<FieldWithScheme> PICA_FIELDS_WITH_SCHEME = Arrays.asList(
    new FieldWithScheme("041A", "Schlagwortfolgen (DNB und Verbünde)"),
    new FieldWithScheme("044K", "Schlagwortfolgen (GBV, SWB, K10plus)"),
    new FieldWithScheme("044L", "Einzelschlagwörter (Projekte)"),
    new FieldWithScheme("044N", "Schlagwörter aus einem Thesaurus und freie Schlagwörter"),
    new FieldWithScheme("044S", "Gattungsbegriffe bei Alten Drucken"),
    new FieldWithScheme("044Z", "Lokale Schlagwörter auf bibliografischer Ebene"),
    new FieldWithScheme("045A", "LCC-Notation"),
    new FieldWithScheme("045B/00", "Allgemeine Systematik für Bibliotheken (ASB)"),
    new FieldWithScheme("045B/01", "Systematik der Stadtbibliothek Duisburg (SSD)"),
    new FieldWithScheme("045B/02", "Systematik für Bibliotheken (SfB)"),
    new FieldWithScheme("045B/03", "Klassifikation für Allgemeinbibliotheken (KAB)"),
    new FieldWithScheme("045B/04", "Systematiken der ekz"),
    new FieldWithScheme("045B/05", "Gattungsbegriffe (DNB)"),
    new FieldWithScheme("045C", "Klassifikation der National Library of Medicine (NLM)"),
    // TODO: 045D/00-29 - "STW-Schlagwörter"
    // TODO: 045D/30-39 - "STW-Schlagwörter - automatisierte verbale Sacherschließung"
    // TODO: 045D/40-48 - "STW-Schlagwörter - Platzhalter"
    new FieldWithScheme("045D/49", "ZBW-Schlagwörter - Veröffentlichungsart"),
    new FieldWithScheme("045D/50", "Vorläufige Schhlagwörter (STW)"),
    new FieldWithScheme("045D/60", "FIV-Schlagwörter (Themen)"),
    new FieldWithScheme("045D/70", "FIV-Schlagwörter (Aspekte)"),
    new FieldWithScheme("045E", "Sachgruppen der Deutschen Nationalbibliografie bis 2003"),
    new FieldWithScheme("045F", "DDC-Notation"),
    new FieldWithScheme("045G", "Sachgruppen der Deutschen Nationalbibliografie ab 2004"),
    new FieldWithScheme("045H", "DDC-Notation: Vollständige Notation"),
    new FieldWithScheme("045M", "Lokale Notationen auf bibliografischer Ebene"),
    new FieldWithScheme("045N", "FIV-Regionalklassifikation"),
    new FieldWithScheme("045Q/01", "Basisklassifikation"),
    new FieldWithScheme("045R", "Regensburger Verbundklassifikation (RVK)"),
    new FieldWithScheme("045S", "Deutsche Bibliotheksstatistik (DBS)"),
    new FieldWithScheme("045T", "Nicht mehr gültige Notationen der Regensburger Verbundklassifikation (RVK)"),
    new FieldWithScheme("045V", "SSG-Nummer/FID-Kennzeichen"),
    new FieldWithScheme("045W", "SSG-Angabe für thematische OLC-Ausschnitte"),
    new FieldWithScheme("045X", "Notation eines Klassifikationssystems"),
    new FieldWithScheme("045Y", "SSG-Angabe für Fachkataloge")
  );

  public ClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics) {
    this.marcRecord = marcRecord;
    this.statistics = statistics;
    if (marcRecord.getSchemaType().equals(SchemaType.PICA) && manager == null) {
      manager = PicaVocabularyManager.getInstance();
    }
  }

  public ClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics, ClassificationParameters parameters) {
    this(marcRecord, statistics);
    this.parameters = parameters;
  }

  public int process() {
    var total = 0;
    schemasInRecord = new ArrayList<>();

    if (marcRecord.getSchemaType().equals(SchemaType.MARC21)) {
      total = processFieldsWithIndicator1AndSubfield2(total);
      total = processFieldsWithIndicator2AndSubfield2(total);
      total = processFieldsWithSubfield2(total);
      total = processFieldsWithoutSource(total);
      total = processFieldsWithScheme(total, MARC21_FIELD_WITH_SCHEMES);
    } else if (marcRecord.getSchemaType().equals(SchemaType.PICA)) {
      total = processFieldsWithSchemePica(total, PICA_FIELDS_WITH_SCHEME);
    }

    increaseCounters(total);

    return total;
  }

  private void increaseCounters(int total) {
    count((total > 0), statistics.getHasClassifications());
    count(total, statistics.getSchemaHistogram());
    statistics.getFrequencyExamples().computeIfAbsent(total, s -> marcRecord.getId(true));

    if (parameters != null && parameters.doCollectCollocations()) {
      List<String> collocation = getCollocationInRecord();
      if (!collocation.isEmpty())
        count(collocation, statistics.getCollocationHistogram());
    }
  }

  private int processFieldsWithScheme(int total, List<FieldWithScheme> fieldsWithScheme) {
    for (FieldWithScheme fieldWithScheme : fieldsWithScheme) {
      var count = processFieldWithScheme(marcRecord, fieldWithScheme);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithSchemePica(int total, List<FieldWithScheme> fieldsWithScheme) {
    int count = total;
    for (VocabularyEntry entry : manager.getAll()) {
      if (!marcRecord.hasDatafield(entry.getPica()))
        continue;

      String schema = entry.getLabel();
      List<DataField> fields = marcRecord.getDatafield(entry.getPica());
      List<Schema> schemas = new ArrayList<>();
      for (DataField field : fields) {
        String firstSubfield = null;
        if (field.getSubfield("a") != null) {
          firstSubfield = "$a";
        } else {
          for (MarcSubfield subfield : field.getSubfields()) {
            String code = subfield.getCode();
            if (!code.equals("A")) {
              firstSubfield = "$" + code;
              break;
            }
          }
        }
        if (firstSubfield != null) {
          var currentSchema = new Schema(field.getTagWithOccurrence(), firstSubfield, entry.getVoc(), schema);
          schemas.add(currentSchema);
          updateSchemaSubfieldStatistics(field, currentSchema);
          count++;
        } else {
          logger.log(Level.SEVERE, "undetected subfield in record {0} {1}", new Object[]{marcRecord.getId(), field.toString()});
        }
      }
      registerSchemas(schemas);
    }
    return count;
  }

  private int processFieldsWithoutSource(int total) {
    for (String tag : fieldsWithoutSource) {
      var count = processFieldWithoutSource(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithSubfield2(int total) {
    for (String tag : fieldsWithSubfield2) {
      var count = processFieldWithSubfield2(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithIndicator2AndSubfield2(int total) {
    for (String tag : fieldsWithIndicator2AndSubfield2) {
      var count = processFieldWithIndicator2AndSubfield2(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldsWithIndicator1AndSubfield2(int total) {
    for (String tag : fieldsWithIndicator1AndSubfield2) {
      var count = processFieldWithIndicator1AndSubfield2(marcRecord, tag);
      if (count > 0)
        total += count;
    }
    return total;
  }

  private int processFieldWithScheme(BibliographicRecord marcRecord,
                                     FieldWithScheme fieldEntry) {
    var count = 0;
    final String tag = fieldEntry.getTag();
    if (!marcRecord.hasDatafield(tag))
      return count;

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
        var scheme = fieldEntry.getSchemaName();
        var currentSchema = new Schema(
          tag, firstSubfield, classificationSchemes.resolve(scheme), scheme);
        schemas.add(currentSchema);
        updateSchemaSubfieldStatistics(field, currentSchema);
        count++;
      } else {
        logger.log(Level.SEVERE, "undetected subfield in record {} {}", new Object[]{marcRecord.getId(), field.toString()});
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

  private int processFieldWithIndicator1AndSubfield2(BibliographicRecord marcRecord, String tag) {
    var count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

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
      } else {
        try {
          currentSchema = new Schema(tag, "ind1", classificationSchemes.resolve(scheme), scheme);
        } catch (IllegalArgumentException e) {
          logger.log(Level.SEVERE, "Invalid scheme in ind1: {}. {}", new Object[]{e.getLocalizedMessage(), field});
          currentSchema = new Schema(tag, "ind1", field.getInd1(), scheme);
        }
        schemas.add(currentSchema);
      }
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return count;
  }

  private int processFieldWithIndicator2AndSubfield2(BibliographicRecord marcRecord, String tag) {
    var count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

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
          logger.log(Level.WARNING, "Invalid scheme in ind2: {}. {}", new Object[]{e.getLocalizedMessage(), field});
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

  private int processFieldWithSubfield2(BibliographicRecord marcRecord, String tag) {
    var count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      var currentSchema = extractSchemaFromSubfield2(tag, schemas, field);
      updateSchemaSubfieldStatistics(field, currentSchema);
      count++;
    }

    registerSchemas(schemas);

    return count;
  }

  private int processFieldWithoutSource(BibliographicRecord marcRecord, String tag) {
    var count = 0;
    if (!marcRecord.hasDatafield(tag))
      return count;

    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      var abbreviavtion = field.getInd2().equals(" ") ? "#" : field.getInd2();
      var currentSchema = new Schema(tag, "ind2", "uncontrolled/" + abbreviavtion, field.resolveInd2());
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

    statistics.getSubfields().computeIfAbsent(currentSchema, s -> new HashMap<>());
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
    List<Schema> deduplicated = new ArrayList<>();
    deduplicated.addAll(new HashSet<>(schemas));
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
    if (!schemes.isEmpty())
      for (Schema scheme : schemes)
        Utils.count(scheme, fieldStatistics);
  }

  private void addSchemesToStatistics(Map<String[], Integer> fieldStatistics,
                                      List<String[]> schemes) {
    if (!schemes.isEmpty()) {
      for (String[] scheme : schemes) {
        if (!fieldStatistics.containsKey(scheme)) {
          fieldStatistics.put(scheme, 0);
          statistics.getFieldInRecords().computeIfAbsent(scheme, s -> 0);
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
    statistics.getFieldInstances().computeIfAbsent(field, s -> new HashMap<>());
    return statistics.getFieldInstances().get(field);
  }

  public List<Schema> getSchemasInRecord() {
    return schemasInRecord;
  }

  public List<String> getCollocationInRecord() {
    return schemasInRecord
      .stream()
      .map(Schema::getAbbreviation)
      .sorted()
      .distinct()
      .collect(Collectors.toList());
  }
}
