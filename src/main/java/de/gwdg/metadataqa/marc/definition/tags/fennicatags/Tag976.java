package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#976
 */
public class Tag976 extends DataFieldDefinition {

  private static Tag976 uniqueInstance;

  private Tag976() {
    initialize();
    postCreation();
  }

  public static Tag976 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag976();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "976";
    label = "KATEGORIA";
    mqTag = "Kategoria";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#976";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Pääkategoria", "NR",
      "b", "Alakategoria", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
