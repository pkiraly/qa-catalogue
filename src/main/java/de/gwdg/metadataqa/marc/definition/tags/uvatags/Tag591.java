package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Abstracts
 */
public class Tag591 extends DataFieldDefinition {
  private static Tag591 uniqueInstance;

  private Tag591() {
    initialize();
    postCreation();
  }

  public static Tag591 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag591();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "591";
    label = "Local Abstracts";
    mqTag = "Local Abstracts";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Local Abstracts (incl. thesis HvA)", "NR"
    );

    getSubfield("a").setMqTag("LocalAbstracts");
  }
}
