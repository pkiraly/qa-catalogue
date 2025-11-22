package de.gwdg.metadataqa.marc.definition.tags.genttags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag920 extends DataFieldDefinition {

  private static Tag920 uniqueInstance;

  private Tag920() {
    initialize();
    postCreation();
  }

  public static Tag920 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag920();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "920";
    label = "Used in the union catalog of Belgium";
    mqTag = "DefinedForUnionCatalogOfBelgium";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Value", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
