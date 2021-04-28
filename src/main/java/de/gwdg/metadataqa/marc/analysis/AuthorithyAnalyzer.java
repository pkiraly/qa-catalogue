package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;

import java.util.EnumMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static de.gwdg.metadataqa.marc.Utils.add;
import static de.gwdg.metadataqa.marc.Utils.count;

public class AuthorithyAnalyzer {

  private static final Logger logger = Logger.getLogger(
    AuthorithyAnalyzer.class.getCanonicalName()
  );
  private static final Pattern NUMERIC = Pattern.compile("^\\d");

  private MarcRecord marcRecord;
  private AuthorityStatistics authoritiesStatistics;

  public AuthorithyAnalyzer(MarcRecord marcRecord,
                            AuthorityStatistics authoritiesStatistics) {
    this.marcRecord = marcRecord;
    this.authoritiesStatistics = authoritiesStatistics;
  }

  public int process() {
    Map<AuthorityCategory, Integer> categoryCounter = new EnumMap<>(AuthorityCategory.class);
    var count = 0;
    for (DataField field : marcRecord.getAuthorityFields()) {
      var type = field.getDefinition().getSourceSpecificationType();
      if (type != null) {
        if (type.equals(SourceSpecificationType.Subfield2)) {
          int fieldInstanceLevelCount = processFieldWithSubfield2(field);
          count += fieldInstanceLevelCount;
          add(AuthorityCategory.get(field.getTag()), categoryCounter, fieldInstanceLevelCount);
        } else {
          logger.log(Level.SEVERE, "Unhandled type: {0}", type);
        }
      }
    }
    updateAuthorityCategoryStatitics(categoryCounter);
    return count;
  }

  private void updateAuthorityCategoryStatitics(Map<AuthorityCategory, Integer> categoryCounter) {
    for (Map.Entry<AuthorityCategory, Integer> entry : categoryCounter.entrySet()) {
      if (entry.getValue() > 0) {
        // logger.info(entry.getKey() + " -> " * )
        authoritiesStatistics.getInstancesPerCategories().add(entry.getKey(), entry.getValue());
        authoritiesStatistics.getRecordsPerCategories().count(entry.getKey());
      }
    }
  }

  private int processFieldWithSubfield2(DataField field) {
    var count = 0;
    List<Schema> schemas = new ArrayList<>();
    var currentSchema = extractFromSubfield0(field, schemas);
    if (currentSchema == null)
      currentSchema = extractSchemaFromSubfield2(field.getTag(), schemas, field);
    updateSchemaSubfieldStatistics(field, currentSchema);
    count++;

    addSchemasToStatistics(authoritiesStatistics.getInstances(), schemas);
    addSchemasToStatistics(authoritiesStatistics.getRecords(), deduplicateSchema(schemas));

    return count;
  }

  private Schema extractFromSubfield0(DataField field, List<Schema> schemas) {
    Schema currentSchema = null;
    List<MarcSubfield> subfields = field.getSubfield("0");
    if (subfields != null && !subfields.isEmpty()) {
      for (MarcSubfield subfield : subfields) {
        Map<String, String> content = subfield.parseContent();
        String organization = null;
        String organizationCode = null;
        if (content.containsKey("organization")) {
          organization = content.get("organization");
        } else if (content.containsKey("organizationCode")) {
          organizationCode = content.get("organizationCode");
        }
        if (organizationCode != null) {
          if (organization == null)
            organization = organizationCode;
          currentSchema = new Schema(field.getTag(), "$0", organization, organizationCode);
          schemas.add(currentSchema);
        }
      }
    }
    return currentSchema;
  }

  private Schema extractSchemaFromSubfield2(String tag,
                                            List<Schema> schemas,
                                            DataField field) {
    Schema currentSchema = null;
    List<MarcSubfield> altSchemes = field.getSubfield("2");
    if (altSchemes == null || altSchemes.isEmpty()) {
      currentSchema = new Schema(tag, "$2", "undetectable", "undetectable");
      schemas.add(currentSchema);
    } else {
      for (MarcSubfield altScheme : altSchemes) {
        currentSchema = new Schema(tag, "$2", altScheme.getValue(), altScheme.resolve());
        schemas.add(currentSchema);
      }
    }
    return currentSchema;
  }

  private void updateSchemaSubfieldStatistics(DataField field,
                                              Schema currentSchema) {
    if (currentSchema == null)
      return;
    List<String> subfields = orderSubfields(field.getSubfields());

    authoritiesStatistics.getSubfields().computeIfAbsent(currentSchema, s -> new HashMap<>());
    Map<List<String>, Integer> subfieldsStatistics = authoritiesStatistics.getSubfields().get(currentSchema);
    if (!subfieldsStatistics.containsKey(subfields)) {
      subfieldsStatistics.put(subfields, 1);
    } else {
      subfieldsStatistics.put(subfields, subfieldsStatistics.get(subfields) + 1);
    }
  }

  private void addSchemasToStatistics(Map<Schema, Integer> fieldStatistics, List<Schema> schemes) {
    if (!schemes.isEmpty())
      for (Schema scheme : schemes)
        count(scheme, fieldStatistics);
  }

  private List<String> orderSubfields(List<MarcSubfield> originalSubfields) {
    List<String> subfields = new ArrayList<>();
    Set<String> multiFields = new HashSet<>();
    for (MarcSubfield subfield : originalSubfields) {
      String code = subfield.getCode();
      if (!subfields.contains(code))
        subfields.add(code);
      else
        multiFields.add(code);
    }
    if (!multiFields.isEmpty()) {
      for (String code : multiFields)
        subfields.remove(code);
      for (String code : multiFields)
        subfields.add(code + "+");
    }

    List<String> alphabetic = new ArrayList<>();
    List<String> numeric = new ArrayList<>();
    for (String subfield : subfields) {
      if (NUMERIC.matcher(subfield).matches()) {
        numeric.add(subfield);
      } else {
        alphabetic.add(subfield);
      }
    }
    if (!numeric.isEmpty()) {
      Collections.sort(alphabetic);
      Collections.sort(numeric);
      subfields = alphabetic;
      subfields.addAll(numeric);
    } else {
      Collections.sort(subfields);
    }
    return subfields;
  }

  private List<Schema> deduplicateSchema(List<Schema> schemas) {
    List<Schema> deduplicated = new ArrayList<>();
    deduplicated.addAll(new HashSet<>(schemas));
    return deduplicated;
  }
}
