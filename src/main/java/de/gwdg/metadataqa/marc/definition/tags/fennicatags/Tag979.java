package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#979
 */
public class Tag979 extends DataFieldDefinition {

  private static Tag979 uniqueInstance;

  private Tag979() {
    initialize();
    postCreation();
  }

  public static Tag979 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag979();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "979";
    label = "OSAKOHTEET";
    mqTag = "Osakohteet";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#979";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Osakohdetietueen ID", "NR",
      "b", "Nimeke", "NR",
      "c", "Tekijä", "NR",
      "d", "Muut tekijät", "NR",
      "e", "Yhtenäistetty nimeke", "NR",
      "f", "Kestoaika", "NR",
      "g", "Muut nimekkeet", "NR",
      "h", "Kielet", "NR",
      "i", "Alkuperäiset kielet", "NR",
      "j", "Tekstityskielet", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
