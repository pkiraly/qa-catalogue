package de.gwdg.metadataqa.marc.analysis.classification;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.ClassificationStatistics;
import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Marc21ClassificationAnalyzer extends ClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    Marc21ClassificationAnalyzer.class.getCanonicalName()
  );
  public static final String DEWEY_DECIMAL_CLASSIFICATION = "Dewey Decimal Classification";

  /**
   * Consists of fields which have the first indicator which states the classification source (scheme). The first
   * indicator can also indicate that the source is specified in the subfield 2.
   */
  private static final List<String> fieldsWithIndicator1AndSubfield2 = Arrays.asList(
    "052", // Geographic Classification
    "086"  // Government Document Classification Number
  );

  // 055 $2 -- Used only when the second indicator contains value
  // 6 (Other call number assigned by LAC),
  // 7 (Other class number assigned by LAC),
  // 8 (Other call number assigned by the contributing library),
  // 9 (Other class number assigned by the contributing library).
  /**
   * Consists of fields which have the second indicator which states the classification source (scheme). The second
   * indicator can also indicate that the source is specified in the subfield 2.
   */
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

  /**
   * Consists of fields which have the subfield 2 which states the classification source (scheme).
   */
  private static final List<String> fieldsWithSubfield2 = Arrays.asList(
    "084", // Other Classificaton Number
    "654", // Subject Added Entry - Faceted Topical Terms
    "658", // Index Term - Curriculum Objective
    "662"  // Subject Added Entry - Hierarchical Place Name
  );

  private static final List<String> fieldsWithoutSource = List.of(
    "653"  // Index Term - Uncontrolled
  );

  /**
   * Fields which directly specify the classification scheme in the field.
   */
  private static final List<FieldWithScheme> MARC21_FIELD_WITH_SCHEMES = Arrays.asList(
    new FieldWithScheme("080", "Universal Decimal Classification"),
    new FieldWithScheme("082", DEWEY_DECIMAL_CLASSIFICATION),
    new FieldWithScheme("083", DEWEY_DECIMAL_CLASSIFICATION),
    new FieldWithScheme("085", DEWEY_DECIMAL_CLASSIFICATION)
    // new FieldWithScheme("086", "Government Document Classification");
  );

  public Marc21ClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics) {
    super(marcRecord, statistics);
  }

  public Marc21ClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics, ClassificationParameters parameters) {
    this(marcRecord, statistics);
    this.parameters = parameters;
  }

  @Override
  public int process() {
    var total = 0;
    schemasInRecord = new ArrayList<>();

    total += processFieldsWithIndicator1AndSubfield2();
    total += processFieldsWithIndicator2AndSubfield2();
    total += processFieldsWithSubfield2();
    total += processFieldsWithoutSource();
    total += processFieldsWithScheme(MARC21_FIELD_WITH_SCHEMES);

    increaseCounters(total);

    return total;
  }

  private int processFieldsWithIndicator1AndSubfield2() {
    int totalCount = 0;
    for (String tag : fieldsWithIndicator1AndSubfield2) {
      var count = processFieldWithIndicator1AndSubfield2(marcRecord, tag);
      totalCount += count;
    }
    return totalCount;
  }

  private int processFieldsWithIndicator2AndSubfield2() {
    int total = 0;
    for (String tag : fieldsWithIndicator2AndSubfield2) {
      var count = processFieldWithIndicator2AndSubfield2(marcRecord, tag);
      total += count;
    }
    return total;
  }

  private int processFieldsWithSubfield2() {
    int total = 0;
    for (String tag : fieldsWithSubfield2) {
      var count = processFieldWithSubfield2(marcRecord, tag);
      total += count;
    }
    return total;
  }

  private int processFieldsWithScheme(List<FieldWithScheme> fieldsWithScheme) {
    int total = 0;
    for (FieldWithScheme fieldWithScheme : fieldsWithScheme) {
      var count = processFieldWithScheme(marcRecord, fieldWithScheme);
      total += count;
    }
    return total;
  }

  private int processFieldsWithoutSource() {
    int total = 0;
    for (String tag : fieldsWithoutSource) {
      var count = processFieldWithoutSource(marcRecord, tag);
      total += count;
    }
    return total;
  }

  private int processFieldWithScheme(BibliographicRecord marcRecord,
                                     FieldWithScheme fieldEntry) {
    final String tag = fieldEntry.getTag();
    if (!marcRecord.hasDatafield(tag)) {
      return 0;
    }

    // Get the fields with the fieldEntry tag from the current record
    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    // For each field that corresponds to the fieldEntry tag, extract the scheme
    for (DataField field : fields) {
      String firstSubfield = null;

      // For each subfield in the current field, extract the first subfield that is not 1, 2, 6, or 8
      // Not sure why it's accessed this way instead of just getting the subfield $a?
      for (MarcSubfield subfield : field.getSubfields()) {
        String code = subfield.getCode();
        if (!code.equals("1")
          && !code.equals("2")
          && !code.equals("6")
          && !code.equals("8")) {
          firstSubfield = "$" + code;
          break;
        }
      }

      if (firstSubfield == null) {
        logger.log(Level.SEVERE, "undetected subfield in record {0} {1}", new Object[]{marcRecord.getId(), field});
        continue;
      }

      var scheme = fieldEntry.getSchemaName();
      var currentSchema = new Schema(tag, firstSubfield, classificationSchemes.resolve(scheme), scheme);
      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return schemas.size();
  }

  /**
   * For all fields defined in fieldsWithIndicator1AndSubfield2, process the field and update the statistics.
   * If the first indicator has a reference to subfield 2, extract the scheme from the subfield 2.
   * Otherwise, extract the scheme from the first indicator of the field.
   * @param marcRecord The current record to extract schemes from
   * @param tag The tag of the current field where the scheme is extracted from
   * @return The number of schemes extracted from the current field
   */
  private int processFieldWithIndicator1AndSubfield2(BibliographicRecord marcRecord, String tag) {
    var count = 0;
    if (!marcRecord.hasDatafield(tag)) {
      return count;
    }

    List<Schema> schemas = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafield(tag);
    for (DataField field : fields) {
      // Get the scheme from the first indicator of the current field
      String scheme = field.resolveInd1();
      if (scheme.equals("No information provided")) {
        continue;
      }

      count++;

      if (isaReferenceToSubfield2(tag, scheme)) {
        List<Schema> extractedSchemas = extractSchemasFromSubfield2(tag, field);
        schemas.addAll(extractedSchemas);

        // For each schema extracted from the subfield 2, update the statistics
        for (Schema extractedSchema : extractedSchemas) {
          updateSchemaSubfieldStatistics(field, extractedSchema);
        }
        continue;
      }
      Schema currentSchema;
      try {
        currentSchema = new Schema(tag, "ind1", classificationSchemes.resolve(scheme), scheme);
      } catch (IllegalArgumentException e) {
        logger.log(Level.SEVERE, "Invalid scheme in ind1: {0}. {1}", new Object[]{e.getLocalizedMessage(), field});
        currentSchema = new Schema(tag, "ind1", field.getInd1(), scheme);
      }

      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return count;
  }

  private int processFieldWithIndicator2AndSubfield2(BibliographicRecord marcRecord, String tag) {
    var count = 0;
    if (!marcRecord.hasDatafield(tag)) {
      return count;
    }

    List<Schema> schemas = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafield(tag);
    for (DataField field : fields) {
      String scheme = field.resolveInd2();
      if (scheme.equals("No information provided")) {
        continue;
      }

      count++;

      if (isaReferenceToSubfield2(tag, scheme)) {
        List<Schema> extractedSchemas = extractSchemasFromSubfield2(tag, field);
        schemas.addAll(extractedSchemas);

        // For each schema extracted from the subfield 2, update the statistics
        for (Schema extractedSchema : extractedSchemas) {
          updateSchemaSubfieldStatistics(field, extractedSchema);
        }
        continue;
      }

      Schema currentSchema;
      try {
        currentSchema = new Schema(tag, "ind2", classificationSchemes.resolve(scheme), scheme);
      } catch (IllegalArgumentException e) {
        logger.log(Level.WARNING, "Invalid scheme in ind2: {0}. {1}", new Object[]{e.getLocalizedMessage(), field});
        currentSchema = new Schema(tag, "ind2", field.getInd2(), scheme);
      }
      schemas.add(currentSchema);

      updateSchemaSubfieldStatistics(field, currentSchema);
    }
    registerSchemas(schemas);

    return count;
  }

  private int processFieldWithSubfield2(BibliographicRecord marcRecord, String tag) {
    var count = 0;
    if (!marcRecord.hasDatafield(tag)) {
      return count;
    }

    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      List<Schema> extractedSchemas = extractSchemasFromSubfield2(tag, field);
      schemas.addAll(extractedSchemas);

      for (Schema extractedSchema : extractedSchemas) {
        updateSchemaSubfieldStatistics(field, extractedSchema);
      }
      count++;
    }

    registerSchemas(schemas);

    return count;
  }

  /**
   * For the specified field, update the statistics so that the scheme is labeled as uncontrolled along with the
   * abbreviation (or the name of the classification provided) from the second indicator.
   * @param marcRecord The current record to extract schemes from
   * @param tag The tag of the current field where the scheme is extracted from
   * @return The number of schemes extracted from the current field
   */
  private int processFieldWithoutSource(BibliographicRecord marcRecord, String tag) {
    if (!marcRecord.hasDatafield(tag)) {
      return 0;
    }

    // Get all fields that correspond to the tag
    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();

    // For each corresponding field, state that the schema is uncontrolled along with the abbreviation (or the name
    // of the classification provided) from the second indicator
    // E.g. for 653, that indicator could be "Topical term", "Personal name", "Corporate name", etc.
    for (DataField field : fields) {
      var abbreviation = field.getInd2().equals(" ") ? "#" : field.getInd2();
      var currentSchema = new Schema(tag, "ind2", "uncontrolled/" + abbreviation, field.resolveInd2());
      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return schemas.size();
  }

  /**
   * Extracts the scheme from the subfield 2 of the current field.
   * If the subfield 2 is not present, a new schema is created with the value "undetectable".
   * Otherwise, a new schema is created for each subfield 2 found.
   * @param tag The tag of the current field
   * @param field The current field from which the subfield 2 is extracted
   * @return The schemas extracted from the subfield 2
   */
  private List<Schema> extractSchemasFromSubfield2(String tag,
                                                   DataField field) {

    List<Schema> extractedSchemas = new ArrayList<>();
    List<MarcSubfield> altSchemes = field.getSubfield("2");

    // If the subfield 2 is not present, we cannot extract the scheme
    if (altSchemes == null || altSchemes.isEmpty()) {
      Schema newSchema = new Schema(tag, "$2", "undetectable", "undetectable");
      extractedSchemas.add(newSchema);
      return extractedSchemas;
    }

    for (MarcSubfield altScheme : altSchemes) {
      Schema newSchema = new Schema(tag, "$2", altScheme.getValue(), altScheme.resolve());
      extractedSchemas.add(newSchema);
    }
    return extractedSchemas;
  }

  /**
   * Checks for the current field if the scheme source is a reference to subfield 2. In other words,
   * if the source is specified in subfield $2.
   * @param field The current field to check
   * @param scheme Name of the scheme or reference to subfield 2. For 055: LC-based call number assigned by LAC,
   *               Complete LC class number assigned by LAC, Incomplete LC class number assigned by LAC, etc.
   * @return True if the scheme source is a reference to subfield 2, false otherwise.
   */
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
}
