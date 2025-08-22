package de.gwdg.metadataqa.marc.analysis.contextual.authority;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.contextual.ContextualAnalyzer;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class AuthorityAnalyzer extends ContextualAnalyzer<AuthorityStatistics> {

  private static final Logger logger = Logger.getLogger(AuthorityAnalyzer.class.getCanonicalName());
  public static final String UNDETECTABLE = "undetectable";

  protected AuthorityAnalyzer(BibliographicRecord bibliographicRecord, AuthorityStatistics authoritiesStatistics) {
    super(bibliographicRecord, authoritiesStatistics);
  }

  /**
   * Process the authority fields in the bibliographic record and update the statistics.
   * @return The number of authority fields processed
   */
  public int process() {
    Map<AuthorityCategory, Integer> categoryCounter = new EnumMap<>(AuthorityCategory.class);
    var count = 0;
    for (Map.Entry<DataField, AuthorityCategory> entry : bibliographicRecord.getAuthorityFieldsMap().entrySet()) {
      DataField field = entry.getKey();
      AuthorityCategory category = entry.getValue();

      count += processField(field, category, categoryCounter);
    }
    updateAuthorityCategoryStatistics(categoryCounter);
    return count;
  }

  protected abstract int processField(DataField field, AuthorityCategory category, Map<AuthorityCategory, Integer> categoryCounter);

  private void updateAuthorityCategoryStatistics(Map<AuthorityCategory, Integer> categoryCounter) {
    for (Map.Entry<AuthorityCategory, Integer> entry : categoryCounter.entrySet()) {
      if (entry.getValue() <= 0) {
        continue;
      }

      statistics.getInstancesPerCategories().add(entry.getKey(), entry.getValue());
      statistics.getRecordsPerCategories().count(entry.getKey());
    }
  }

  protected List<Schema> extractSchemasFromSubfield2(String tag,
                                                     DataField field) {

    List<Schema> extractedSchemas = new ArrayList<>();
    List<MarcSubfield> altSchemes = field.getSubfield("2");

    // If the subfield 2 is not present, we cannot extract the scheme
    if (altSchemes == null || altSchemes.isEmpty()) {
      Schema newSchema = new Schema(tag, "$2", UNDETECTABLE, UNDETECTABLE);
      extractedSchemas.add(newSchema);
      return extractedSchemas;
    }

    for (MarcSubfield altScheme : altSchemes) {
      Schema newSchema = new Schema(tag, "$2", altScheme.getValue(), altScheme.resolve());
      extractedSchemas.add(newSchema);
    }
    return extractedSchemas;
  }
}
