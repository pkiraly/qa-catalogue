package de.gwdg.metadataqa.marc.analysis.contextual.authority;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.List;
import java.util.Map;

public class UnimarcAuthorityAnalyzer extends AuthorityAnalyzer {

  public UnimarcAuthorityAnalyzer(BibliographicRecord marcRecord, AuthorityStatistics authoritiesStatistics) {
    super(marcRecord, authoritiesStatistics);
  }

  @Override
  protected int processField(DataField field, AuthorityCategory category, Map<AuthorityCategory, Integer> categoryCounter) {
    int fieldInstanceLevelCount = processFieldWithSubfield2(field);
    Utils.addToCounter(category, categoryCounter, fieldInstanceLevelCount);
    return fieldInstanceLevelCount;
  }

  private int processFieldWithSubfield2(DataField field) {
    // Even though the subfield $2 in UNIMARC authority fields usually doesn't contain schema information,
    // for now, the same logic as in the MARC21 analyzer is used to extract schemas from subfield $2.
    List<Schema> schemas = extractSchemasFromSubfield2(field.getTag(), field);
    for (Schema schema : schemas) {
      updateSchemaSubfieldStatistics(field, schema);
    }

    registerSchemas(schemas);
    return schemas.size();
  }
}
