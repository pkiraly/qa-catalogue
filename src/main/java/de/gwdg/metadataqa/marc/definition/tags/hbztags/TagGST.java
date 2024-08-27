package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Topical Term (GST) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on MARC Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664284/450.pdf
 * https://www.alma-dach.org/alma-marc/authority/450/450.html
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
      "a", "Sachbegriff", "NR",
      "g", "Identifizierender Zusatz", "R",
      "i", "Beziehungsphrase", "NR",
      "w", "Kontrollunterfeld", "NR",
      "x", "Allgemeine Unterteilung ", "R",
      "5", "Institution, auf die sich das Feld bezieht", "R",
      "9", "C: - Anwendungskontext (W), L: - Sprachcode (NW), U: - Schriftcode (NW), v: - Bemerkungen (NW)", "R",
      "A", "Vocabulary subfield", "R",
      "B", "Authority ID subfield", "R",
      "C", "Authority tag subfield","R"
    );
  }
}
