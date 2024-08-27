package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Corporate Name (GKT) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on MARC Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664309/410.pdf
 * https://www.alma-dach.org/alma-marc/authority/410/410.html
 */
public class TagGKT extends DataFieldDefinition {

  private static TagGKT uniqueInstance;

  private TagGKT() {
    initialize();
    postCreation();
  }

  public static TagGKT getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagGKT();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "GKT";
    label = "Corporate Name (GKT) - GND 410";
    mqTag = "GNDCorporateName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator("Art Körperschaftsnamens")
    .setCodes(
      "1", "Name einer Gebietskörperschaft",
      "2", "Name einer Körperschaft"
    )
    .setMqTag("artKoerperschaftsnamens");
    
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Hauptkörperschaft", "NR",
      "b", "Untergeordnete Körperschaft", "R",
      "e", "Funktionsbezeichnung", "R",
      "g", "Zusatz", "R",
      "i", "Beziehungsphrase", "NR",
      "n", "Zählung einer Körperschaft", "R",
      "w", "Kontrollunterfeld", "NR",
      "x", "Allgemeine Unterteilung", "R",
      "4", "Beziehungscode", "R",
      "5", "Institution, auf die sich das Feld bezieht", "R",
      "9", "C: - Anwendungskontext (W), L: - Sprachcode (NW), U: - Schriftcode (NW), v: - Bemerkungen (W)", "R",
      "A", "Vocabulary subfield", "R",
      "B", "Authority ID subfield", "R",
      "C", "Authority tag subfield","R"
    );
  }
}
