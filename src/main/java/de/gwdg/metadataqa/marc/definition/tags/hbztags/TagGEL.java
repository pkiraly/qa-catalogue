package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Uniform Title (GEL) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on PICA+ Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/167033591/430.pdf
 */
public class TagGEL extends DataFieldDefinition {

  private static TagGEL uniqueInstance;

  private TagGEL() {
    initialize();
    postCreation();
  }

  public static TagGEL getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagGEL();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "GEL";
    label = "Uniform Title (GEL) - GND 430";
    mqTag = "GNDUniformTitle";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "4", "GND-Code für Beziehungen", "NR",
        "5", "Institution (ISIL), die Feld in besonderer Art verwendet", "R",
        "a", "Titel eines Werks", "NR",
        "f", "Datum", "NR",
        "g", "Zusatz", "R",
        "h", "Inhaltstyp", "R",
        "l", "Sprache der Expression", "R",
        "L", "Sprachencode", "NR",
        "m", "Besetzung im Musikbereich", "NR",
        "n", "Zählung eines Teils/einer Abteilung eines Werks", "R",
        "o", "Angaben des Musikarrangements", "NR",
        "p", "Titel eines Teils/einer Abteilung eines Werks", "R",
        "r", "Tonart", "NR",
        "s", "Version", "NR",
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
