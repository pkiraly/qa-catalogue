package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Meeting Name (GKS) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on MARC Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664310/411.pdf
 * https://www.alma-dach.org/alma-marc/authority/411/411.html
 */
public class TagGKS extends DataFieldDefinition {

  private static TagGKS uniqueInstance;

  private TagGKS() {
    initialize();
    postCreation();
  }

  public static TagGKS getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagGKS();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "GKS";
    label = "Meeting Name (GKS) - GND 411";
    mqTag = "GNDMeetingName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator("Art des Veranstaltungsnamens als Eintragungselement")
    .setCodes(
      "2", "Name einer Veranstaltung"
    )
    .setMqTag("artDesVeranstaltungsnamensAlsEintragungselement");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Hauptkonferenzname", "NR",
      "c", "Ort der Veranstaltung", "NR",
      "d", "Datum", "NR",
      "e", "Untergeordnete Einheit", "R",
      "g", "Zusatz", "R",
      "i", "Beziehungsphrase", "NR",
      "j", "Funktionsbezeichnung", "R",
      "n", "Zählung", "R",
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
