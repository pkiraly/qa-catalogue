package de.gwdg.metadataqa.marc.analysis.contextual.classification;

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

public class Marc21ClassificationAnalyzer extends MarcClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    Marc21ClassificationAnalyzer.class.getCanonicalName()
  );
  public static final String DEWEY_DECIMAL_CLASSIFICATION = "Dewey Decimal Classification";

  /**
   * Consists of tags of fields which have the first indicator which states the classification source (schema).
   * The first indicator can also indicate that the source is specified in the subfield 2.
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
   * Consists of tags of fields which have the second indicator which states the classification source (schema).
   * The second indicator can also indicate that the source is specified in the subfield 2.
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
   * Consists of tags of  fields which have the subfield 2 which states the classification source (schema).
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
   * Fields which directly specify the classification schema in the field.
   */
  private static final List<FieldWithScheme> MARC21_FIELD_WITH_SCHEMES = Arrays.asList(
    new FieldWithScheme("080", "Universal Decimal Classification"),
    new FieldWithScheme("082", DEWEY_DECIMAL_CLASSIFICATION),
    new FieldWithScheme("083", DEWEY_DECIMAL_CLASSIFICATION),
    new FieldWithScheme("085", DEWEY_DECIMAL_CLASSIFICATION)
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
    logger.log(Level.INFO, "Classifying MARC21 record");
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
      var count = processFieldWithIndicatorAndSubfield2(bibliographicRecord, tag, true);
      totalCount += count;
    }
    return totalCount;
  }

  private int processFieldsWithIndicator2AndSubfield2() {
    int total = 0;
    for (String tag : fieldsWithIndicator2AndSubfield2) {
      var count = processFieldWithIndicatorAndSubfield2(bibliographicRecord, tag, false);
      total += count;
    }
    return total;
  }

  private int processFieldsWithSubfield2() {
    int total = 0;
    for (String tag : fieldsWithSubfield2) {
      var count = processFieldWithSubfield2(bibliographicRecord, tag);
      total += count;
    }
    return total;
  }

  private int processFieldsWithScheme(List<FieldWithScheme> fieldsWithSchema) {
    int total = 0;
    for (FieldWithScheme fieldWithScheme : fieldsWithSchema) {
      var count = processFieldWithSchema(bibliographicRecord, fieldWithScheme);
      total += count;
    }
    return total;
  }

  private int processFieldsWithoutSource() {
    int total = 0;
    for (String tag : fieldsWithoutSource) {
      var count = processFieldWithoutSource(bibliographicRecord, tag);
      total += count;
    }
    return total;
  }

  /**
   * Process the given field (usually from the lists fieldsWithIndicatorXAndSubfield2) and extract the schema.
   * If the given indicator has a reference to subfield 2, extract the schema from the subfield 2.
   * Otherwise, extract the schema from that indicator of the field.
   * @param marcRecord The current record to extract schemas from
   * @param tag The tag of the current field where the schema is extracted from
   * @param isInd1 True if the indicator is the first indicator, false if it's the second indicator
   * @return The number of schemas extracted from the current field
   */
  private int processFieldWithIndicatorAndSubfield2(BibliographicRecord marcRecord, String tag, boolean isInd1) {
    if (!marcRecord.hasDatafield(tag)) {
      return 0;
    }

    List<Schema> schemas = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafieldsByTag(tag);

    for (DataField field : fields) {
      List<Schema> extractedSchemas = extractSchemas(field, tag, isInd1);

      schemas.addAll(extractedSchemas);
      for (Schema extractedSchema : extractedSchemas) {
        updateSchemaSubfieldStatistics(field, extractedSchema);
      }
    }

    registerSchemas(schemas);

    return schemas.size();
  }

  /**
   * Extracts the schemas from the given field. If the schema source is a reference to subfield 2, extract the schemas
   * from the subfield 2. Otherwise, extract the schema from the specified indicator of the field.
   * @param field The current field to extract the schemas from
   * @param tag The tag of the current field
   * @param isInd1 True if the indicator is the first indicator, false if it's the second indicator
   * @return The schemas extracted from the current field
   */
  private List<Schema> extractSchemas(DataField field, String tag, boolean isInd1) {
    String schema;

    if (isInd1) {
      schema = field.resolveInd1();
    } else {
      schema = field.resolveInd2();
    }

    if (schema.equals("No information provided")) {
      return List.of();
    }

    String indicatorLabel = isInd1 ? "ind1" : "ind2";

    if (isaReferenceToSubfield2(tag, schema)) {
      return extractSchemasFromSubfield2(tag, field);
    }

    Schema currentSchema;
    try {
      String schemaLabel = classificationSchemes.resolve(schema);
      currentSchema = new Schema(tag, indicatorLabel, schemaLabel, schema);
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, "Invalid schema in {0}: {1}. {2}",
        new Object[]{indicatorLabel, e.getLocalizedMessage(), field});

      String indicatorValue = isInd1 ? field.getInd1() : field.getInd2();
      currentSchema = new Schema(tag, indicatorLabel, indicatorValue, schema);
    }

    return List.of(currentSchema);
  }

  /**
   * For the specified field, update the statistics so that the schema is labeled as uncontrolled along with the
   * abbreviation (or the name of the classification provided) from the second indicator.
   * @param marcRecord The current record to extract schemas from
   * @param tag The tag of the current field where the schema is extracted from
   * @return The number of schemas extracted from the current field
   */
  private int processFieldWithoutSource(BibliographicRecord marcRecord, String tag) {
    if (!marcRecord.hasDatafield(tag)) {
      return 0;
    }

    // Get all fields that correspond to the tag
    List<DataField> fields = marcRecord.getDatafieldsByTag(tag);
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
   * Checks for the current field if the schema source is a reference to subfield 2. In other words,
   * if the source is specified in subfield $2.
   * @param field The current field to check
   * @param schema Name of the schema or reference to subfield 2. For 055: LC-based call number assigned by LAC,
   *               Complete LC class number assigned by LAC, Incomplete LC class number assigned by LAC, etc.
   * @return True if the schema source is a reference to subfield 2, false otherwise.
   */
  private boolean isaReferenceToSubfield2(String field, String schema) {
    return (
         (field.equals("055") && isAReferenceFrom055(schema))
      || schema.equals("Source specified in subfield $2"));
  }

  private boolean isAReferenceFrom055(String schema) {
    return (schema.equals("Other call number assigned by LAC")
      || schema.equals("Other class number assigned by LAC")
      || schema.equals("Other call number assigned by the contributing library")
      || schema.equals("Other class number assigned by the contributing library"));
  }
}
