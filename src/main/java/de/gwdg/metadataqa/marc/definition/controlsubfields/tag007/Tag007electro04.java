package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Dimensions
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro04 extends ControlSubfieldDefinition {
  private static Tag007electro04 uniqueInstance;

  private Tag007electro04() {
    initialize();
    extractValidCodes();
  }

  public static Tag007electro04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007electro04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Dimensions";
    id = "tag007electro04";
    mqTag = "dimensions";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
    codes = Utils.generateCodes(
      "a", "3 1/2 in.",
      "e", "12 in.",
      "g", "4 3/4 in. or 12 cm.",
      "i", "1 1/8 x 2 3/8 in.",
      "j", "3 7/8 x 2 1/2 in.",
      "n", "Not applicable",
      "o", "5 1/4 in.",
      "u", "Unknown",
      "v", "8 in.",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
  }
}