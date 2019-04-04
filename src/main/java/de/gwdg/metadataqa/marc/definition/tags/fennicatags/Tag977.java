package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#977
 */
public class Tag977 extends DataFieldDefinition {

  private static Tag977 uniqueInstance;

  private Tag977() {
    initialize();
    postCreation();
  }

  public static Tag977 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag977();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "977";
    label = "AINEISTOTYYPPI";
    mqTag = "Aineistotyyppi";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#977";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Aineistotyyppi", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
