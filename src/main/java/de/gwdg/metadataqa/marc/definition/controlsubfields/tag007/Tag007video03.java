package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Color
 * https://www.loc.gov/marc/bibliographic/bd007v.html
 */
public class Tag007video03 extends ControlSubfieldDefinition {
  private static Tag007video03 uniqueInstance;

  private Tag007video03() {
    initialize();
    extractValidCodes();
  }

  public static Tag007video03 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007video03();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Color";
    id = "tag007video03";
    mqTag = "color";
    positionStart = 3;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007v.html";
    codes = Utils.generateCodes(
      "a", "One color",
      "b", "Black-and-white",
      "c", "Multicolored",
      "m", "Mixed",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
  }
}