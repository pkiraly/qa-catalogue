package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * OCLC Control Number Cross-Reference
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#019
 */
public class Tag019 extends DataFieldDefinition {

  private static Tag019 uniqueInstance;

  private Tag019() {
    initialize();
    postCreation();
  }

  public static Tag019 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag019();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "019";
    label = "FINUC-TUNNUS";
    mqTag = "FINUCTunnus";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#019";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Finuc-tunnus", "NR"
    );
    // TODO: "The badge begins with small fs, followed by six digits."

    getSubfield("a").setMqTag("rdf:value");
  }
}
