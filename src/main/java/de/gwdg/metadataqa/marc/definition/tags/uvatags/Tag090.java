package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

/**
 * Abbreviated Title
 * http://www.loc.gov/marc/bibliographic/bd210.html
 */
public class Tag090 extends DataFieldDefinition {
  private static Tag090 uniqueInstance;

  private Tag090() {
    initialize();
    postCreation();
  }

  public static Tag090 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag090();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "090";
    label = "Locally Assigned LC-type Call Number";
    mqTag = "LocallyAssignedLCtypeCallNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = null;
    ind2 = null;

    setSubfieldsWithCardinality(
      "a", "Classification number", "R",
      "b", "Item number", "NR"
    );

    getSubfield("a").setMqTag("ClassificationNumber");
    getSubfield("b").setMqTag("ItemNumber");
  }
}
