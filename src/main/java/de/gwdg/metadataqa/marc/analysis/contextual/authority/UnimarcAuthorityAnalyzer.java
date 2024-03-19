package de.gwdg.metadataqa.marc.analysis.contextual.authority;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UnimarcAuthorityAnalyzer extends AuthorityAnalyzer {
  private static final Logger logger = Logger.getLogger(UnimarcAuthorityAnalyzer.class.getCanonicalName());

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
    // TODO: Implement the extraction of schemas in the specified fields. I'm however not sure if that's even included
    //  in the UNIMARC format.
    Schema unhandledSchema = new Schema(field.getTag(), "$2", "UNHANDLED", "UNHANDLED");

    updateSchemaSubfieldStatistics(field, unhandledSchema);

    registerSchemas(List.of(unhandledSchema));
    return 1;
  }
}
