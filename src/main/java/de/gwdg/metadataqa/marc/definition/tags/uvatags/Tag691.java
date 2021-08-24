package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

/**
 * Abbreviated Title
 * http://www.loc.gov/marc/bibliographic/bd210.html
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
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = null;
    ind2 = null;

    setSubfieldsWithCardinality(
      "a", "NBC (terms)", "NR"
    );

    getSubfield("a").setMqTag("NBC");
  }
}
