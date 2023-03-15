package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

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

  private static final Logger logger = Logger.getLogger(AuthorithyAnalyzer.class.getCanonicalName());
  private static final Pattern NUMERIC = Pattern.compile("^\\d");
  public static final String UNDETECTABLE = "undetectable";

  private BibliographicRecord marcRecord;
  private AuthorityStatistics authoritiesStatistics;

  public AuthorithyAnalyzer(BibliographicRecord marcRecord,
                            AuthorityStatistics authoritiesStatistics) {
    this.marcRecord = marcRecord;
    this.authoritiesStatistics = authoritiesStatistics;
  }

  public int process() {
    Map<AuthorityCategory, Integer> categoryCounter = new EnumMap<>(AuthorityCategory.class);
    var count = 0;
    for (Map.Entry<DataField, AuthorityCategory> entry : marcRecord.getAuthorityFieldsMap().entrySet()) {
      DataField field = entry.getKey();
      AuthorityCategory category = entry.getValue();
      if (marcRecord.getSchemaType().equals(SchemaType.MARC21)) {
        var type = field.getDefinition().getSourceSpecificationType();
        if (type != null) {
          if (type.equals(SourceSpecificationType.Subfield2)) {
            var fieldInstanceLevelCount = processFieldWithSubfield2(field);
            count += fieldInstanceLevelCount;
            add(category, categoryCounter, fieldInstanceLevelCount);
          } else {
            logger.log(Level.SEVERE, "Unhandled type: {0}", type);
          }
        }
      } else if (marcRecord.getSchemaType().equals(SchemaType.PICA)) {
        var fieldInstanceLevelCount = processPicaField(field);
        count += fieldInstanceLevelCount;
        add(category, categoryCounter, fieldInstanceLevelCount);
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

  private int processPicaField(DataField field) {
    var count = 0;
    List<Schema> schemas = new ArrayList<>();
    var currentSchema = extractSchemaFromSubfield7(field.getTagWithOccurrence(), schemas, field);
    if (currentSchema == null)
      currentSchema = extractSchemaFromSubfield2(field.getTagWithOccurrence(), schemas, field);
    updateSchemaSubfieldStatistics(field, currentSchema);
    count++;

    addSchemasToStatistics(authoritiesStatistics.getInstances(), schemas);
    addSchemasToStatistics(authoritiesStatistics.getRecords(), deduplicateSchema(schemas));

    return count;
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
      currentSchema = new Schema(tag, "$2", UNDETECTABLE, UNDETECTABLE);
      schemas.add(currentSchema);
    } else {
      for (MarcSubfield altScheme : altSchemes) {
        currentSchema = new Schema(tag, "$2", altScheme.getValue(), altScheme.resolve());
        schemas.add(currentSchema);
      }
    }
    return currentSchema;
  }

  private Schema extractSchemaFromSubfield7(String tag,
                                            List<Schema> schemas,
                                            DataField field) {
    Schema currentSchema = null;
    List<MarcSubfield> altSchemes = field.getSubfield("7");
    if (altSchemes == null || altSchemes.isEmpty()) {
      currentSchema = new Schema(tag, "$7", UNDETECTABLE, UNDETECTABLE);
      schemas.add(currentSchema);
    } else {
      for (MarcSubfield altScheme : altSchemes) {
        if (altScheme.getValue().contains("/")) {
          String[] parts = altScheme.getValue().split("/");
          var code = SubjectHeadingAndTermSourceCodes.getInstance().getCode(parts[0]);
          var label = code == null ? parts[0] : code.getLabel();
          currentSchema = new Schema(tag, "$7", parts[0], label);
        } else {
          currentSchema = new Schema(tag, "$7", UNDETECTABLE, UNDETECTABLE);
        }
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
