package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicaRecord extends BibliographicRecord {

  private static List<String> authorityTags;
  private static List<String> claasificationTags;
  private static Map<String, Boolean> authorityTagsIndex;
  private static Map<String, Boolean> claasificationTagsIndex;
  private static Map<String, Map<String, Boolean>> authorityTagsSkippableSubfields;
  private static Map<String, Map<String, Boolean>> claasificationTagsSkippableSubfields;
  private static Map<AuthorityCategory, List<String>> authorityTagsMap;

  public PicaRecord() {
    super();
    init();
  }

  public PicaRecord(String id) {
    super(id);
    init();
  }

  private void init() {
    schemaType = SchemaType.PICA;
  }

  public List<DataField> getAuthorityFields() {
    if (authorityTags == null) {
      initializeAuthorityTags();
    }
    return getAuthorityFields(authorityTags);
  }

  public boolean isAuthorityTag(String tag) {
    if (authorityTagsIndex == null) {
      initializeAuthorityTags();
    }
    return authorityTagsIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableAuthoritySubfield(String tag, String code) {
    if (authorityTagsIndex == null)
      initializeAuthorityTags();

    if (!authorityTagsSkippableSubfields.containsKey(tag))
      return false;

    // System.err.println();
    return authorityTagsSkippableSubfields.get(tag).getOrDefault(code, false);
  }

  public boolean isClassificationTag(String tag) {
    if (claasificationTagsIndex == null) {
      initializeAuthorityTags();
    }
    return claasificationTagsIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableClassificationSubfield(String tag, String code) {
    if (claasificationTagsIndex == null)
      initializeAuthorityTags();

    if (!claasificationTagsSkippableSubfields.containsKey(tag))
      return false;

    // System.err.println();
    return claasificationTagsSkippableSubfields.get(tag).getOrDefault(code, false);
  }

  public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
    if (authorityTags == null) {
      initializeAuthorityTags();
    }
    return getAuthorityFields(authorityTagsMap);
  }


  private static void initializeAuthorityTags() {
    authorityTags = Arrays.asList(
      "022A", // Werktitel und sonstige unterscheidende Merkmale des Werks
      "022A", // Weiterer Werktitel und sonstige unterscheidende Merkmale
      "028A", // Person/Familie als 1. geistiger Schöpfer
      "028B", // 2. und weitere Verfasser
      "028C", // Person/Familie als 2. und weiterer geistiger Schöpfer, sonstige Personen/Familien, die mit dem Werk in Verbindung stehen, Mitwirkende, Hersteller, Verlage, Vertriebe
      "028E", // Interpret
      "028G", // Sonstige Person/Familie
      "029A", // Körperschaft als 1. geistiger Schöpfer
      "029E", // Körperschaft als Interpret
      "029F", // Körperschaft als 2. und weiterer geistiger Schöpfer, sonstige Körperschaften, die mit dem Werk in Verbindung stehen, Mitwirkende, Hersteller, Verlage, Vertriebe
      "029G", // Sonstige Körperschaft
      "032V", // Sonstige unterscheidende Eigenschaften des Werks
      "032W", // Form des Werks
      "032X", // Besetzung
      "033D", // Normierter Ort
      "033H", // Verbreitungsort in normierter Form
      "033J", // Drucker, Verleger oder Buchhändler (bei Alten Drucken)
      "037Q", // Beschreibung des Einbands
      "037R"  // Buchschmuck (Druckermarken, Vignetten, Zierleisten etc.)
    );
    authorityTagsIndex = Utils.listToMap(authorityTags);

    authorityTagsSkippableSubfields = new HashMap<>();
    authorityTagsSkippableSubfields.put("022A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("028A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("028B", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("028C", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("028E", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("028G", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("029A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("029E", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("029F", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("029G", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("033D", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("033H", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    authorityTagsSkippableSubfields.put("033J", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));

    claasificationTags = Arrays.asList(
      "045A", "045B", "045F", "045R", "045C", "045E", "045G"
    );
    claasificationTagsIndex = Utils.listToMap(claasificationTags);
    claasificationTagsSkippableSubfields = new HashMap<>();
    claasificationTagsSkippableSubfields.put("022A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    claasificationTagsSkippableSubfields.put("045R", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));

    authorityTagsMap = new HashMap<>();
    authorityTagsMap.put(AuthorityCategory.Titles, List.of("022A", "022A"));
    authorityTagsMap.put(AuthorityCategory.Personal, List.of("028A", "028B", "028C", "028E", "028G", "033J"));
    authorityTagsMap.put(AuthorityCategory.Corporate, List.of("029A", "029E", "029F", "029G"));
    authorityTagsMap.put(AuthorityCategory.Other, List.of("032V", "032W", "032X", "037Q", "037R"));
    authorityTagsMap.put(AuthorityCategory.Geographic, List.of("033D", "033H"));
  }
}
