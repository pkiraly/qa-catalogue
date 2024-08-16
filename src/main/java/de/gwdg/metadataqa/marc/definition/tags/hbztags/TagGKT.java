package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Corporate Name (GKT) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on PICA+ Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664309/410.pdf
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

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "4", "GND-Code für Beziehungen", "NR",
        "5", "Institution (ISIL), die Feld in besonderer Art verwendet", "R",
        "a", "Hauptkörperschaft", "NR",
        "b", "Untergeordnete Körperschaft", "R",
        "g", "Zusatz", "R",
        "L", "Sprachencode", "NR",
        "n", "Zählung", "R",
        "v", "Bemerkungen, Regelwerk", "R",
        "x", "Allgemeine Unterteilung (temporär durch Migration)", "R",
        "T", "Feldzuordnung bei nicht-lateinischen Schriftzeichen", "NR",
        "U", "Schriftcode bei nicht-lateinischen Schriftzeichen", "NR",
        "A", "Vocabulary subfield", "R",
        "B", "Authority ID subfield", "R",
        "C", "Authority tag subfield","R"
    );
  }
}
