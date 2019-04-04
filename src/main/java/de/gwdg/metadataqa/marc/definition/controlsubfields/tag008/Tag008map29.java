package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008p.html
 */
public class Tag008map29 extends ControlSubfieldDefinition {
  private static Tag008map29 uniqueInstance;

  private Tag008map29() {
    initialize();
    extractValidCodes();
  }

  public static Tag008map29 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008map29();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of item";
    id = "tag008map29";
    mqTag = "formOfItem";
    positionStart = 29;
    positionEnd = 30;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008p.html";
    codes = Utils.generateCodes(
      " ", "None of the following",
      "a", "Microfilm",
      "b", "Microfiche",
      "c", "Microopaque",
      "d", "Large print",
      "f", "Braille",
      "o", "Online",
      "q", "Direct electronic",
      "r", "Regular print reproduction",
      "s", "Electronic",
      "|", "No attempt to code"
    );
  }
}