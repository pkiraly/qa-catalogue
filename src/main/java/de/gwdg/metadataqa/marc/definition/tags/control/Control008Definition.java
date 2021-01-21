package de.gwdg.metadataqa.marc.definition.tags.control;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control008Positions;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd008.html
 */
public class Control008Definition extends ControlFieldDefinition {

  private static Control008Definition uniqueInstance;

  private Control008Definition() {
    initialize();
    postCreation();
  }

  public static Control008Definition getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control008Definition();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "008";
    label = "General Information";
    mqTag = "GeneralInformation";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008.html";
    controlfieldPositions = Control008Positions.getInstance();
  }
}
