package de.gwdg.metadataqa.marc.analysis.contextual.authority;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Marc21AuthorityAnalyzer extends AuthorityAnalyzer {
  private static final Logger logger = Logger.getLogger(Marc21AuthorityAnalyzer.class.getCanonicalName());

  public Marc21AuthorityAnalyzer(BibliographicRecord marcRecord, AuthorityStatistics authoritiesStatistics) {
    super(marcRecord, authoritiesStatistics);
  }

  @Override
  protected int processField(DataField field, AuthorityCategory category, Map<AuthorityCategory, Integer> categoryCounter) {
    var type = field.getDefinition().getSourceSpecificationType();
    if (type == null) {
      return 0;
    }

    if (!type.equals(SourceSpecificationType.Subfield2)) {
      logger.log(Level.SEVERE, "Unhandled type: {0}", new Object[]{type});
      return 0;
    }

    int fieldInstanceLevelCount = processFieldWithSubfield2(field);
    Utils.addToCounter(category, categoryCounter, fieldInstanceLevelCount);
    return fieldInstanceLevelCount;
  }

  private int processFieldWithSubfield2(DataField field) {
    List<Schema> schemas;
    schemas = extractSchemasFromSubfield0(field);
    if (schemas.isEmpty()) {
      schemas = extractSchemasFromSubfield2(field.getTag(), field);
    }

    for (Schema schema : schemas) {
      updateSchemaSubfieldStatistics(field, schema);
    }

    registerSchemas(schemas);
    return schemas.size();
  }

  private List<Schema> extractSchemasFromSubfield0(DataField field) {
    List<MarcSubfield> subfields = field.getSubfield("0");
    if (subfields == null || subfields.isEmpty()) {
      return List.of();
    }

    List<Schema> schemas = new ArrayList<>();
    for (MarcSubfield subfield : subfields) {
      Map<String, String> content = subfield.parseContent();
      String organization = null;
      String organizationCode = null;
      // FIXME: Organization is never considered. It always checks if the organizationCode is present in the
      //  if (organizationCode == null) block below
      if (content.containsKey("organization")) {
        organization = content.get("organization");
      } else if (content.containsKey("organizationCode")) {
        organizationCode = content.get("organizationCode");
      }

      if (organizationCode == null) {
        continue;
      }

      organization = organizationCode;
      Schema currentSchema = new Schema(field.getTag(), "$0", organization, organizationCode);
      schemas.add(currentSchema);
    }

    return schemas;
  }

}
