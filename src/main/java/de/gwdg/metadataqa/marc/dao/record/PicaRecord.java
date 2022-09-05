package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicaRecord extends BibliographicRecord {

  private static List<String> authorityTags;
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
    authorityTagsMap = new HashMap<>();
    authorityTagsMap.put(AuthorityCategory.Titles, List.of("022A", "022A"));
    authorityTagsMap.put(AuthorityCategory.Personal, List.of("028A", "028B", "028C", "028E", "028G", "033J"));
    authorityTagsMap.put(AuthorityCategory.Corporate, List.of("029A", "029E", "029F", "029G"));
    authorityTagsMap.put(AuthorityCategory.Other, List.of("032V", "032W", "032X", "037Q", "037R"));
    authorityTagsMap.put(AuthorityCategory.Geographic, List.of("033D", "033H"));
  }
}
