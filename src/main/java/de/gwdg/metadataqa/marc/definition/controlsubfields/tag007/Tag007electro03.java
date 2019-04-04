package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Color
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro03 extends ControlSubfieldDefinition {
  private static Tag007electro03 uniqueInstance;

  private Tag007electro03() {
    initialize();
    extractValidCodes();
  }

  public static Tag007electro03 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007electro03();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Color";
    id = "tag007electro03";
    mqTag = "color";
    positionStart = 3;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
    codes = Utils.generateCodes(
      "a", "One color",
      "b", "Black-and-white",
      "c", "Multicolored",
      "g", "Gray scale",
      "m", "Mixed",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "h", "Hand coloured [OBSOLETE, 1997] [CAN/MARC only]"
    );
  }
}