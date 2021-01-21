package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * OCLC Control Number Cross-Reference
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#019
 */
public class Tag579 extends DataFieldDefinition {

  private static Tag579 uniqueInstance;

  private Tag579() {
    initialize();
    postCreation();
  }

  public static Tag579 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag579();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "579";
    label = "TILASTOINTIMERKINTÄ - VOYAGER-KENTTÄ";
    mqTag = "Tilastointimerkintä";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#579";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Tilastointimerkintä", "NR",
      "b", "Tietokannan tunnus", "NR"
    );

    getSubfield("a")
      .setMqTag("rdf:value");
  }
}
