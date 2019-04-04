package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#908
 */
public class Tag908 extends DataFieldDefinition {

  private static Tag908 uniqueInstance;

  private Tag908() {
    initialize();
    postCreation();
  }

  public static Tag908 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag908();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "908";
    label = "KANSALLISUUSKOODI";
    mqTag = "Kansallisuuskoodi";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#908";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Koodi", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
