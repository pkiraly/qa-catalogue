package de.gwdg.metadataqa.marc.analysis.classification;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.ClassificationStatistics;
import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Primarily used to encompass methods that are common to MARC classification analyzers (Marc21ClassificationAnalyzer
 * and UnimarcClassificationAnalyzer), so that code duplication is minimized.
 */
public abstract class MarcClassificationAnalyzer extends ClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    MarcClassificationAnalyzer.class.getCanonicalName()
  );

  protected MarcClassificationAnalyzer(BibliographicRecord bibliographicRecord, ClassificationStatistics statistics) {
    super(bibliographicRecord, statistics);
  }

  /**
   * The source is specified in the subfield 2. For each field with the specified tag, extract the schema from the
   * subfield 2 and update the statistics.
   * @param marcRecord The current record to extract schemas from
   * @param tag The tag of the current field where the schema is extracted from
   * @return The number of schemas extracted from the current field
   */
  protected int processFieldWithSubfield2(BibliographicRecord marcRecord, String tag) {
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
   * Extracts the scheme from the subfield 2 of the current field.
   * If the subfield 2 is not present, a new schema is created with the value "undetectable".
   * Otherwise, a new schema is created for each subfield 2 found.
   * @param tag The tag of the current field
   * @param field The current field from which the subfield 2 is extracted
   * @return The schemas extracted from the subfield 2
   */
  protected List<Schema> extractSchemasFromSubfield2(String tag,
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

  protected int processFieldWithSchema(BibliographicRecord marcRecord,
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


}
