package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Geographic Name(GGN) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on MARC Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664311/451.pdf
 * https://www.alma-dach.org/alma-marc/authority/451/451.html
 */
public class TagGGN extends DataFieldDefinition {

  private static TagGGN uniqueInstance;

  private TagGGN() {
    initialize();
    postCreation();
  }

  public static TagGGN getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagGGN();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "GGN";
    label = "Geographic Name (GGN) - GND 451";
    mqTag = "GNDGeographicName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name eines Geografikums", "NR",
      "g", "Zusatz", "R",
      "i", "Beziehungsphrase", "NR",
      "w", "Kontrollunterfeld", "NR",
      "x", "Allgemeine Unterteilung", "R",
      "z", "Geografische Unterteilung", "R",
      "4", "Beziehungscode", "R",
      "5", "Institution, auf die sich das Feld bezieht", "R",
      "9", "C: - Anwendungskontext (W), L: - Sprachcode (NW), U: - Schriftcode (NW), v: - Bemerkungen (NW)", "R",
      "A", "Vocabulary subfield", "R",
      "B", "Authority ID subfield", "R",
      "C", "Authority tag subfield","R"
    );
  }
}
