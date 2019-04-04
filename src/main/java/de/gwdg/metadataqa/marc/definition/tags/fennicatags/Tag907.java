package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#907
 */
public class Tag907 extends DataFieldDefinition {

  private static Tag907 uniqueInstance;

  private Tag907() {
    initialize();
    postCreation();
  }

  public static Tag907 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag907();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "907";
    label = "YLE-KOODI";
    mqTag = "YLEKoodi";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#907";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Koodi", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
