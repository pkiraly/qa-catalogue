package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.Crosswalk;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.PicaMarcCrosswalkReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PicaRecord extends BibliographicRecord {

  private static List<String> authorityTags;
  private static List<String> subjectTags;
  private static Map<String, Boolean> authorityTagsIndex;
  private static Map<String, Boolean> subjectTagIndex;
  private static Map<String, Map<String, Boolean>> skippableAuthoritySubfields;
  private static Map<String, Map<String, Boolean>> skippableSubjectSubfields;
  private static Map<AuthorityCategory, List<String>> authorityTagsMap;
  private static Map<ThompsonTraillFields, List<String>> thompsonTraillTagMap;
  private static Map<ShelfReadyFieldsBooks, Map<String, List<String>>> shelfReadyMap;

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

    if (!skippableAuthoritySubfields.containsKey(tag))
      return false;

    return skippableAuthoritySubfields.get(tag).getOrDefault(code, false);
  }

  public boolean isSubjectTag(String tag) {
    if (subjectTagIndex == null) {
      initializeAuthorityTags();
    }
    return subjectTagIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableSubjectSubfield(String tag, String code) {
    if (subjectTagIndex == null)
      initializeAuthorityTags();

    if (!skippableSubjectSubfields.containsKey(tag))
      return false;

    return skippableSubjectSubfields.get(tag).getOrDefault(code, false);
  }

  public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
    if (authorityTags == null)
      initializeAuthorityTags();

    return getAuthorityFields(authorityTagsMap);
  }

  public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
    if (shelfReadyMap == null)
      initializeShelfReadyMap();

    return shelfReadyMap;
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

    skippableAuthoritySubfields = new HashMap<>();
    skippableAuthoritySubfields.put("022A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("028A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("028B", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("028C", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("028E", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("028G", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("029A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("029E", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("029F", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("029G", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("033D", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("033H", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("033J", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));

    subjectTags = Arrays.asList(
      "045A", "045B", "045F", "045R", "045C", "045E", "045G"
    );
    subjectTagIndex = Utils.listToMap(subjectTags);
    skippableSubjectSubfields = new HashMap<>();
    skippableSubjectSubfields.put("022A", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableSubjectSubfields.put("045R", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));

    authorityTagsMap = new HashMap<>();
    authorityTagsMap.put(AuthorityCategory.Titles, List.of("022A", "022A"));
    authorityTagsMap.put(AuthorityCategory.Personal, List.of("028A", "028B", "028C", "028E", "028G", "033J"));
    authorityTagsMap.put(AuthorityCategory.Corporate, List.of("029A", "029E", "029F", "029G"));
    authorityTagsMap.put(AuthorityCategory.Other, List.of("032V", "032W", "032X", "037Q", "037R"));
    authorityTagsMap.put(AuthorityCategory.Geographic, List.of("033D", "033H"));
  }

  private static void initializeShelfReadyMap() {
    shelfReadyMap = new LinkedHashMap<>();
    for (Map.Entry<ShelfReadyFieldsBooks, Map<String, List<String>>> entry : (new Marc21Record()).getShelfReadyMap().entrySet()) {
      ShelfReadyFieldsBooks category = entry.getKey();
      shelfReadyMap.put(category, new HashMap<>());
      for (Map.Entry<String, List<String>> marcEntry : entry.getValue().entrySet()) {
        for (String code : marcEntry.getValue()) {
          for (Crosswalk crosswalk : PicaMarcCrosswalkReader.lookupMarc21(marcEntry.getKey() + " $" + code)) {
            if (!shelfReadyMap.get(category).containsKey(crosswalk.getPica()))
              shelfReadyMap.get(category).put(crosswalk.getPica(), new ArrayList<>());
            shelfReadyMap.get(category).get(crosswalk.getPica()).add(crosswalk.getPicaUf().replace("$", ""));
          }
        }
      }
    }
  }

  public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {
    if (thompsonTraillTagMap == null)
      initializeThompsonTrailTags();

    return thompsonTraillTagMap;
  }

  private void initializeThompsonTrailTags() {
    thompsonTraillTagMap = new LinkedHashMap<>();
    for (Map.Entry<ThompsonTraillFields, List<String>> entry : (new Marc21Record()).getThompsonTraillTagsMap().entrySet()) {
      ThompsonTraillFields category = entry.getKey();
      thompsonTraillTagMap.put(category, new ArrayList<>());
      for (String marcEntry : entry.getValue()) {
        for (Crosswalk crosswalk : PicaMarcCrosswalkReader.lookupMarc21Field(marcEntry)) {
          if (!thompsonTraillTagMap.get(category).contains(crosswalk.getPica()))
            thompsonTraillTagMap.get(category).add(crosswalk.getPica());
        }
      }
    }
  }
}
