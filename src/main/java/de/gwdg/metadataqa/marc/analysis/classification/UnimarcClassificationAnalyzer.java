package de.gwdg.metadataqa.marc.analysis.classification;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.ClassificationStatistics;
import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UnimarcClassificationAnalyzer extends ClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    UnimarcClassificationAnalyzer.class.getCanonicalName()
  );

  /**
   * The majority of the fields from the 6xx UNIMARC block which have the source specified in the subfield 2.
   */
  private static final List<String> fieldsWithSource = List.of(
    "600", // Personal name used as subject
    "601", // Corporate body name used as subject
    "602", // Family name used as subject
    "604", // Name and title used as subject
    "605", // Title used as subject
    "606", // Topical name used as subject
    "607", // Geographical name used as subject
    "608", // Form, genre, or physical characteristics access point
    "615", // Subject category
    "616", // Trademark used as subject
    "631", // Occupation
    "632", // Function
    "686"  // Other class numbers
  );

  private static final List<FieldWithScheme> fieldsWithSchema = List.of(
    new FieldWithScheme("675", "Universal Decimal Classification"),
    new FieldWithScheme("676", "Dewey Decimal Classification"),
    new FieldWithScheme("677", "Library of Congress Classification")
  );

  private static final List<String> fieldsWithoutSource = List.of(
    "610"  // Uncontrolled subject terms
  );

  public UnimarcClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics) {
    super(marcRecord, statistics);
  }

  public UnimarcClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics, ClassificationParameters parameters) {
    this(marcRecord, statistics);
    this.parameters = parameters;
  }

  @Override
  public int process() {
    var total = 0;
    schemasInRecord = new ArrayList<>();

    total += processFieldsWithSource();
    total += processFieldsWithoutSource();
    total += processFieldsWithSchema();

    increaseCounters(total);

    return total;
  }
  private int processFieldsWithSource() {
    int total = 0;
    for (String tag : fieldsWithSource) {
      var count = processFieldWithSource(marcRecord, tag);
      total += count;
    }
    return total;
  }

  private int processFieldsWithSchema() {
    int total = 0;
    for (FieldWithScheme fieldWithSchema : fieldsWithSchema) {
      var count = processFieldWithSchema(marcRecord, fieldWithSchema);
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

  private int processFieldWithSchema(BibliographicRecord marcRecord,
                                     FieldWithScheme fieldEntry) {
    final String tag = fieldEntry.getTag();
    if (!marcRecord.hasDatafield(tag)) {
      return 0;
    }

    // Get the fields with the fieldEntry tag from the current record
    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    // For each field that corresponds to the fieldEntry tag, extract the schema
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

      var schema = fieldEntry.getSchemaName();
      var currentSchema = new Schema(tag, firstSubfield, classificationSchemes.resolve(schema), schema);
      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return schemas.size();
  }

  /**
   * The source is specified in the subfield 2. For each field with the specified tag, extract the schema from the
   * subfield 2 and update the statistics.
   * @param marcRecord The current record to extract schemas from
   * @param tag The tag of the current field where the schema is extracted from
   * @return The number of schemas extracted from the current field
   */
  private int processFieldWithSource(BibliographicRecord marcRecord, String tag) {
    if (!marcRecord.hasDatafield(tag)) {
      return 0;
    }

    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      List<Schema> extractedSchemas = extractSchemasFromSubfield2(tag, field);
      schemas.addAll(extractedSchemas);

      for (Schema extractedSchema : extractedSchemas) {
        updateSchemaSubfieldStatistics(field, extractedSchema);
      }
    }

    registerSchemas(schemas);

    return schemas.size();
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
    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();

    // For each corresponding field, state that the schema is uncontrolled along with the abbreviation (or the name
    // of the classification provided) from the second indicator
    // E.g. for 653, that indicator could be "Topical term", "Personal name", "Corporate name", etc.
    for (DataField field : fields) {
      var currentSchema = new Schema(tag, "ind2", "uncontrolled", field.resolveInd2());
      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return schemas.size();
  }

  /**
   * Extracts the schema from the subfield 2 of the current field.
   * If the subfield 2 is not present, a new schema is created with the value "undetectable".
   * Otherwise, a new schema is created for each subfield 2 found.
   * @param tag The tag of the current field
   * @param field The current field from which the subfield 2 is extracted
   * @return The schemas extracted from the subfield 2
   */
  private List<Schema> extractSchemasFromSubfield2(String tag,
                                                   DataField field) {

    List<Schema> extractedSchemas = new ArrayList<>();
    List<MarcSubfield> altSchemas = field.getSubfield("2");

    // If the subfield 2 is not present, we cannot extract the schema
    if (altSchemas == null || altSchemas.isEmpty()) {
      Schema newSchema = new Schema(tag, "$2", "undetectable", "undetectable");
      extractedSchemas.add(newSchema);
      return extractedSchemas;
    }

    for (MarcSubfield altSchema : altSchemas) {
      Schema newSchema = new Schema(tag, "$2", altSchema.getValue(), altSchema.resolve());
      extractedSchemas.add(newSchema);
    }
    return extractedSchemas;
  }
}
