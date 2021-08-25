package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Fixed Field - Books
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
    label = "Local Fixed Field - Books";
    mqTag = "LocalFixedFieldBooks";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Coded Physical Data Early Printed Books", "NR"
    );

    getSubfield("a").setMqTag("CodedPhysicalDataEarlyPrintedBooks");
  }
}
