package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Personal Name (GPN) - GND 400 from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on PICA+ Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664281/400.pdf
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

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "4", "GND-Code für Beziehungen", "NR",
        "5", "InstitutioNR(=ISIL), die Feld iNRbesonderer Art verwendet", "R",
        "a", "Nachname", "NR",
        "c", "Nachgestelltes Präfix", "NR",
        "d", "Vorname", "NR",
        "g", "Zusatz", "R",
        "l", "Beiname, Gattungsname, Titulatur, Territorium", "NR",
        "L", "Sprachencode", "NR",
        "N", "Zählung", "NR",
        "P", "Persönlicher Name", "NR",
        "v", "Bemerkungen, Regelwerk", "R",
        "x", "Allgemeine Unterteilung", "R"
    );
  }
}
