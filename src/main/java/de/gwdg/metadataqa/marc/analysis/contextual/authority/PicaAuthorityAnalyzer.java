package de.gwdg.metadataqa.marc.analysis.contextual.authority;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PicaAuthorityAnalyzer extends AuthorityAnalyzer {

  public PicaAuthorityAnalyzer(BibliographicRecord bibliographicRecord, AuthorityStatistics authoritiesStatistics) {
    super(bibliographicRecord, authoritiesStatistics);
  }

  @Override
  protected int processField(DataField field, AuthorityCategory category, Map<AuthorityCategory, Integer> categoryCounter) {
    List<Schema> schemas;
    schemas = extractSchemasFromSubfield7(field.getTagWithOccurrence(), field);
    if (schemas.isEmpty()) {
      schemas = extractSchemasFromSubfield2(field.getTagWithOccurrence(), field);
    }

    for (Schema schema : schemas) {
      updateSchemaSubfieldStatistics(field, schema);
    }

    registerSchemas(schemas);
    Utils.addToCounter(category, categoryCounter, schemas.size());
    return schemas.size();
  }

  private List<Schema> extractSchemasFromSubfield7(String tag,
                                                   DataField field) {
    List<Schema> schemas = new ArrayList<>();
    List<MarcSubfield> altSchemes = field.getSubfield("7");
    if (altSchemes == null || altSchemes.isEmpty()) {
      Schema currentSchema = new Schema(tag, "$7", UNDETECTABLE, UNDETECTABLE);
      schemas.add(currentSchema);
      return schemas;
    }

    for (MarcSubfield altScheme : altSchemes) {
      if (!altScheme.getValue().contains("/")) {
        Schema currentSchema = new Schema(tag, "$7", UNDETECTABLE, UNDETECTABLE);
        schemas.add(currentSchema);
        continue;
      }

      String[] parts = altScheme.getValue().split("/");
      var code = SubjectHeadingAndTermSourceCodes.getInstance().getCode(parts[0]);
      var label = code == null ? parts[0] : code.getLabel();
      Schema currentSchema = new Schema(tag, "$7", parts[0], label);
      schemas.add(currentSchema);
    }
    return schemas;
  }
}
