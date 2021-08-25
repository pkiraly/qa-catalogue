package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Old System Number (Local note)
 */
public class Tag901 extends DataFieldDefinition {
  private static Tag901 uniqueInstance;

  private Tag901() {
    initialize();
    postCreation();
  }

  public static Tag901 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag901();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "901";
    label = "Old System Number (Local note)";
    mqTag = "OldSystemNumberLocalNote";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Old System Number", "NR"
    );

    getSubfield("a").setMqTag("OldSystemNumber");
  }
}
