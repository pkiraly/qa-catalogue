package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Virtual Item
 */
public class TagWII extends DataFieldDefinition {

  private static TagWII uniqueInstance;

  private TagWII() {
    initialize();
    postCreation();
  }

  public static TagWII getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagWII();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "WII";
    label = "What Is It?";
    mqTag = "WhatIsIt";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    // TODO: further investigation

  }
}
