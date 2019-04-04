package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Level of contraction
 * https://www.loc.gov/marc/bibliographic/bd007f.html
 */
public class Tag007tactile05 extends ControlSubfieldDefinition {
  private static Tag007tactile05 uniqueInstance;

  private Tag007tactile05() {
    initialize();
    extractValidCodes();
  }

  public static Tag007tactile05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007tactile05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Level of contraction";
    id = "tag007tactile05";
    mqTag = "levelOfContraction";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007f.html";
    codes = Utils.generateCodes(
      "a", "Uncontracted",
      "b", "Contracted",
      "m", "Combination",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
  }
}