package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Topical Term (GST) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on PICA+ Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664284/450.pdf
 */
public class TagGST extends DataFieldDefinition {

  private static TagGST uniqueInstance;

  private TagGST() {
    initialize();
    postCreation();
  }

  public static TagGST getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagGST();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "GST";
    label = "Topical Term (GST) - GND 450";
    mqTag = "GNDTopicalTerm";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "4", "GND-Code für Beziehungen", "NR",
        "5", "Institution (ISIL), die Feld in besonderer Art verwendet", "R",
        "a", "Sachbegriff", "NR",
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
