package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Dutch Basic Classification Codes
 */
public class Tag691 extends DataFieldDefinition {
  private static Tag691 uniqueInstance;

  private Tag691() {
    initialize();
    postCreation();
  }

  public static Tag691 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag691();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "691";
    label = "Dutch Basic Classification Codes";
    mqTag = "DutchBasicClassificationCodes";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "NBC (terms)", "NR"
    );

    getSubfield("a").setMqTag("NBC");
  }
}
