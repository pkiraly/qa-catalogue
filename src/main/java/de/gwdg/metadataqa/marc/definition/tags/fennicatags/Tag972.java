package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#972
 */
public class Tag972 extends DataFieldDefinition {

  private static Tag972 uniqueInstance;

  private Tag972() {
    initialize();
    postCreation();
  }

  public static Tag972 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag972();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "972";
    label = "BTJ-KOODI";
    mqTag = "BTJKoodi";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#972";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Koodi", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
