package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#961
 */
public class Tag961 extends DataFieldDefinition {

  private static Tag961 uniqueInstance;

  private Tag961() {
    initialize();
    postCreation();
  }

  public static Tag961 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag961();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "961";
    label = "KIRJASTOKOHTAINEN HUOMAUTUS KOKOELMASTA";
    mqTag = "KirjastokohtainenHuomautusKokoelmasta";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#961";

    ind1 = new Indicator();
    ind2 = new Indicator("Virtuaalikokoelma")
      .setCodes(
        "8", "Virtuaalikokoelma"
      )
      .setMqTag("virtuaalikokoelma");

    setSubfieldsWithCardinality(
      "a", "Fraasi/Virtuaalikokoelman nimi", "NR",
      "u", "Uniform Resource Identifier", "R",
      "x", "Sisäinen huomautus", "R",
      "z", "Julkinen huomautus", "R"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
