package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * OCLC Control Number Cross-Reference
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#019
 */
public class Tag790 extends DataFieldDefinition {

  private static Tag790 uniqueInstance;

  private Tag790() {
    initialize();
    postCreation();
  }

  public static Tag790 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag790();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "790";
    label = "LINKKIKENTTÄ - VOYAGER-KENTTÄ";
    mqTag = "Linkkikenttä";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#790";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Linkkitieto", "NR",
      "x", "Järjestys", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
