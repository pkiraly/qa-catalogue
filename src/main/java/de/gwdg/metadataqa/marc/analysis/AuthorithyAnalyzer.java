package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AuthorithyAnalyzer {

  private static final Logger logger = Logger.getLogger(
    AuthorithyAnalyzer.class.getCanonicalName()
  );
  private static Pattern NUMERIC = Pattern.compile("^\\d");

  private MarcRecord marcRecord;
  private AuthorityStatistics authoritiesStatistics;

  public AuthorithyAnalyzer(MarcRecord marcRecord,
                            AuthorityStatistics authoritiesStatistics) {
    this.marcRecord = marcRecord;
    this.authoritiesStatistics = authoritiesStatistics;
  }

  public int process() {
    int count = 0;
    for (DataField field : marcRecord.getAuthorityFields()) {
      SourceSpecificationType type = field.getDefinition().getSourceSpecificationType();
      if (type == null) {

      } else if (type.equals(SourceSpecificationType.Subfield2)) {
        count += processFieldWithSubfield2(field);
      } else {
        logger.severe("Unhandled type: " + type);
      }
    }
    return count;
  }

  private int processFieldWithSubfield2(DataField field) {
    int count = 0;
    List<Schema> schemas = new ArrayList<>();

    Schema currentSchema = extractFromSubfield0(field, schemas);
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
    List<String> subfields = orderSubfields(field.parseSubfields());

    if (!authoritiesStatistics.getSubfields().containsKey(currentSchema)) {
      authoritiesStatistics.getSubfields().put(
        currentSchema, new HashMap<List<String>, Integer>()
      );
    }
    // count(subfields, authoritiesStatistics.getSubfields());
    Map<List<String>, Integer> subfieldsStatistics = authoritiesStatistics.getSubfields().get(currentSchema);
    if (!subfieldsStatistics.containsKey(subfields)) {
      subfieldsStatistics.put(subfields, 1);
    } else {
      subfieldsStatistics.put(subfields, subfieldsStatistics.get(subfields) + 1);
    }
  }


  private void addSchemasToStatistics(Map<Schema, Integer> fieldStatistics, List<Schema> schemes) {
    if (!schemes.isEmpty()) {
      for (Schema scheme : schemes) {
        if (!fieldStatistics.containsKey(scheme)) {
          fieldStatistics.put(scheme, 0);
        }
        fieldStatistics.put(scheme, fieldStatistics.get(scheme) + 1);
      }
    }
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
    Set<Schema> set = new HashSet<Schema>(schemas);
    List<Schema> deduplicated = new ArrayList<Schema>();
    deduplicated.addAll(new HashSet<Schema>(schemas));
    return deduplicated;
  }
}
