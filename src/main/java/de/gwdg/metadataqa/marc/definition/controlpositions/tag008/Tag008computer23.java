package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008c.html
 */
public class Tag008computer23 extends ControlfieldPositionDefinition {
  private static Tag008computer23 uniqueInstance;

  private Tag008computer23() {
    initialize();
  }

  public static Tag008computer23 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008computer23();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of item";
    id = "008computer23";
    mqTag = "formOfItem";
    positionStart = 23;
    positionEnd = 24;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008c.html";
    codes = Utils.generateCodes(
      " ", "Unknown or not specified",
      "o", "Online",
      "q", "Direct electronic",
      "|", "No attempt to code"
    );
  }
}