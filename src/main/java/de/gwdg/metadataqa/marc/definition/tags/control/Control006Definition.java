package de.gwdg.metadataqa.marc.definition.tags.control;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control006Subfields;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Control006Definition extends ControlFieldDefinition {

  private static Control006Definition uniqueInstance;

  private Control006Definition() {
    initialize();
    postCreation();
  }

  public static Control006Definition getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control006Definition();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "006";
    label = "Additional Material Characteristics";
    mqTag = "AdditionalMaterialCharacteristics";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    controlSubfields = Control006Subfields.getInstance();
  }
}
