package de.gwdg.metadataqa.marc.definition.tags.control;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;

/**
 * Control Number
 * https://www.loc.gov/marc/bibliographic/bd001.html
 */
public class Control001Definition extends DataFieldDefinition {

  private static Control001Definition uniqueInstance;

  private Control001Definition() {
    initialize();
    postCreation();
  }

  public static Control001Definition getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control001Definition();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "001";
    label = "Control Number";
    mqTag = "ControlNumber";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd001.html";
    functions = Arrays.asList(ManagementIdentify);
  }
}
