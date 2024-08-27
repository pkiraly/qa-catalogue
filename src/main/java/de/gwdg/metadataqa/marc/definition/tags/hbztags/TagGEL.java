package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Uniform Title (GEL) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on MARC Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/167033591/430.pdf
 * https://www.alma-dach.org/alma-marc/authority/430/430.html
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
      "a", "Titel eines Werkes", "NR",
      "f", "Datum eines Werkes", "NR",
      "g", "Zusatz", "R",
      "h", "Inhaltstyp", "NR",
      "i", "Beziehungsphrase", "NR",
      "l", "Sprache der Expression", "NR",
      "m", "Medium der Musikaufführung", "R",
      "n", "Zählung eines Teils/einer Abteilung eines Werkes", "R",
      "o", "Angabe des Musikarrangements", "NR",
      "p", "Titel eines Teils/einer Abteilung eines Werkes", "R",
      "r", "Tonart", "NR",
      "s", "Version", "NR",
      "w", "Kontrollunterfeld", "R",
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
