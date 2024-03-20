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
    int fieldInstanceLevelCount = processFieldSchema(field);
    Utils.addToCounter(category, categoryCounter, fieldInstanceLevelCount);
    return fieldInstanceLevelCount;
  }

  private int processFieldSchema(DataField field) {
    // TODO: Implement the extraction of schemas in the specified fields. I'm however not sure if that's even included
    //  in the UNIMARC format.
    Schema unhandledSchema = new Schema(field.getTag(), "$2", "UNSPECIFIED", "UNSPECIFIED");

    updateSchemaSubfieldStatistics(field, unhandledSchema);

    registerSchemas(List.of(unhandledSchema));
    return 1;
  }
}
