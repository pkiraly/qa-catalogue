package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Meeting Name (GKS) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on PICA+ Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664310/411.pdf
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

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "4", "GND-Code für Beziehungen", "NR",
        "5", "Institution (ISIL), die Feld in besonderer Art verwendet", "R",
        "a", "Hauptkonferenz", "NR",
        "b", "Untergeordnete Einheit", "R",
        "c", "Ort", "R",
        "d", "Datum", "NR",
        "g", "Zusatz", "R",
        "L", "Sprachencode", "NR",
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
