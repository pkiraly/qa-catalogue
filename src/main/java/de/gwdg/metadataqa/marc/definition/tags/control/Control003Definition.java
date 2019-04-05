package de.gwdg.metadataqa.marc.definition.tags.control;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseIdentify;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd003.html
 */
public class Control003Definition extends DataFieldDefinition {

  private static Control003Definition uniqueInstance;

  private Control003Definition() {
    initialize();
    postCreation();
  }

  public static Control003Definition getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control003Definition();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "003";
    label = "Control Number Identifier";
    mqTag = "ControlNumberIdentifier";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd003.html";
    functions = Arrays.asList(UseIdentify);
  }
}
