package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Index on Citation/Reference note
 */
public class Tag906 extends DataFieldDefinition {
  private static Tag906 uniqueInstance;

  private Tag906() {
    initialize();
    postCreation();
  }

  public static Tag906 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag906();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "906";
    label = "Local Index on Citation/Reference note";
    mqTag = "LocalIndexOnCitationOrReferenceNote";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Index on Citation/Reference note", "R"
    );

    getSubfield("a").setMqTag("IndexOnCitationOrReferenceNote");
  }
}
