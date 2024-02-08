package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008v.html
 */
public class Tag008visual29 extends ControlfieldPositionDefinition {
  private static Tag008visual29 uniqueInstance;

  private Tag008visual29() {
    initialize();

  }

  public static Tag008visual29 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008visual29();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of item";
    id = "008visual29";
    mqTag = "formOfItem";
    positionStart = 29;
    positionEnd = 30;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008v.html";
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