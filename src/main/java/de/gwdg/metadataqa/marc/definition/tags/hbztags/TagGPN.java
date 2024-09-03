package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Personal Name (GPN) - GND 400 from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on MARC Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664281/400.pdf
 * https://www.alma-dach.org/alma-marc/authority/400/400.html
 */
public class TagGPN extends DataFieldDefinition {

  private static TagGPN uniqueInstance;

  private TagGPN() {
    initialize();
    postCreation();
  }

  public static TagGPN getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagGPN();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "GPN";
    label = "Personal Name (GPN) - GND 400";
    mqTag = "GNDPersonalName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator("Art der Eintragung des Personennamenelements")
      .setCodes(
        "0", "Vorname",
        "1", "Nachname",
        "3", "Familienname"
      )
      .setMqTag("artDerEintragungDesPersonennamenelements");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Personenname", "NR",
      "b", "Zählung", "NR",
      "c", "Titel und andere Wörter in Verbindung mit einem Namen", "R",
      "d", "Datumsangaben in Verbindung mit einem Namen", "NR",
      "e", "Funktionsbezeichnung", "R",
      "i", "Beziehungsphrase", "NR",
      "w", "Kontrollunterfeld", "NR",
      "x", "Allgemeine Unterteilung", "R",
      "4", "Beziehungscode", "R",
      "5", "Institution, auf die sich das Feld bezieht ", "R",
      "9", "C: - Anwendungskontext (W), L: - Sprachcode (NW), U: - Schriftcode (NW), v: - Bemerkungen (W)", "R",
      "t", "Titel eines Werkes", "NR",
      "f", "Datum eines Werkes", "NR",
      "g", "Zusatz", "R",
      "h", "Inhaltstyp", "R",
      "l", "Sprache der Expression", "R",
      "m", "Medium der Musikaufführung", "R",
      "n", "Zählung eines Teils/einer Abteilung eines Werkes", "R",
      "o", "Angabe des Musikarrangements", "R",
      "p", "Titel eines Teils/einer Abteilung eines Werkes", "R",
      "r", "Tonart", "NR",
      "A", "Vocabulary subfield", "R",
      "B", "Authority ID subfield", "R",
      "C", "Authority tag subfield","R"
    );
  }
}
