package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.shelfready.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.pica.PicaSubjectManager;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.Crosswalk;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.PicaMarcCrosswalkReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PicaRecord extends BibliographicRecord {
  private static final Logger logger = Logger.getLogger(
    PicaRecord.class.getCanonicalName()
  );

  private static List<String> authorityTags;
  private static Map<String, Boolean> authorityTagsIndex;
  private static Map<String, Boolean> subjectTagIndex;
  private static Map<String, Map<String, Boolean>> skippableAuthoritySubfields;
  private static Map<String, Map<String, Boolean>> skippableSubjectSubfields;
  /**
   * Key-value pairs of AuthorityCategory and tags
   */
  private static Map<AuthorityCategory, List<String>> authorityTagsMap;
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

  @Override
  public List<String> getAllowedControlFieldTags() {
    return List.of();
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
      "022A/00", // Werktitel und sonstige unterscheidende Merkmale des Werks
      "022A/01", // Weiterer Werktitel und sonstige unterscheidende Merkmale
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
    skippableAuthoritySubfields.put("022A/00", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableAuthoritySubfields.put("022A/01", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
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

    /*
    List<String> subjectTags = Arrays.asList(
      "045A", "045B", "045F", "045R", "045C", "045E", "045G"
    );
     */
    List<String> subjectTags = PicaSubjectManager.getTags();
    subjectTagIndex = Utils.listToMap(subjectTags);
    skippableSubjectSubfields = new HashMap<>();
    skippableSubjectSubfields.put("022A/00", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableSubjectSubfields.put("022A/01", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableSubjectSubfields.put("044H", Utils.listToMap(Arrays.asList("A"))); // A = Quelle
    skippableSubjectSubfields.put("044S", Utils.listToMap(Arrays.asList("9", "A", "V", "7", "3", "w")));
    skippableSubjectSubfields.put("045F", Utils.listToMap(Arrays.asList("A")));
    skippableSubjectSubfields.put("045G", Utils.listToMap(Arrays.asList("A")));
    skippableSubjectSubfields.put("045X", Utils.listToMap(Arrays.asList("A")));
    skippableSubjectSubfields.put("045Y", Utils.listToMap(Arrays.asList("A")));
    skippableSubjectSubfields.put("045N", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableSubjectSubfields.put("045R", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));
    skippableSubjectSubfields.put("045T", Utils.listToMap(Arrays.asList("9", "V", "7", "3", "w")));

    authorityTagsMap = new EnumMap<>(AuthorityCategory.class);
    authorityTagsMap.put(AuthorityCategory.TITLES, List.of("022A/00", "022A/01"));
    authorityTagsMap.put(AuthorityCategory.PERSONAL, List.of("028A", "028B", "028C", "028E", "028G", "033J"));
    authorityTagsMap.put(AuthorityCategory.CORPORATE, List.of("029A", "029E", "029F", "029G"));
    authorityTagsMap.put(AuthorityCategory.OTHER, List.of("032V", "032W", "032X", "037Q", "037R"));
    authorityTagsMap.put(AuthorityCategory.GEOGRAPHIC, List.of("033D", "033H"));
  }

  private static void initializeShelfReadyMap() {
    shelfReadyMap = new LinkedHashMap<>();
    for (Map.Entry<ShelfReadyFieldsBooks, Map<String, List<String>>> entry : (new Marc21BibliographicRecord()).getShelfReadyMap().entrySet()) {
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

  protected void indexField(DataField dataField) {

    String tag = dataField.getTagWithOccurrence();
    if (tag == null)
      logger.warning("null tag in indexField() " + dataField);

    datafieldIndex.computeIfAbsent(tag, s -> new ArrayList<>());
    datafieldIndex.get(tag).add(dataField);

    if (dataField.getTag() != null && !tag.equals(dataField.getTag())) {
      tag = dataField.getTag();
      datafieldIndex.computeIfAbsent(tag, s -> new ArrayList<>());
      datafieldIndex.get(tag).add(dataField);
    }
  }

}
