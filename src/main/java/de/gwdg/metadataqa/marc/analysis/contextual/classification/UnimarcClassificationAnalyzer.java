package de.gwdg.metadataqa.marc.analysis.contextual.classification;

import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnimarcClassificationAnalyzer extends MarcClassificationAnalyzer {

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

  public UnimarcClassificationAnalyzer(BibliographicRecord bibliographicRecord, ClassificationStatistics statistics) {
    super(bibliographicRecord, statistics);
  }

  public UnimarcClassificationAnalyzer(BibliographicRecord bibliographicRecord, ClassificationStatistics statistics, ClassificationParameters parameters) {
    this(bibliographicRecord, statistics);
    this.parameters = parameters;
  }

  @Override
  public int process() {
    logger.log(Level.INFO, "Classifying UNIMARC record");
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
      var count = processFieldWithSubfield2(bibliographicRecord, tag);
      total += count;
    }
    return total;
  }

  private int processFieldsWithSchema() {
    int total = 0;
    for (FieldWithScheme fieldWithSchema : fieldsWithSchema) {
      var count = processFieldWithSchema(bibliographicRecord, fieldWithSchema);
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
      var currentSchema = new Schema(tag, "ind2", "uncontrolled", field.resolveInd2());
      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);

    return schemas.size();
  }
}
