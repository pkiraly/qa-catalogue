package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Dimensions
 * https://www.loc.gov/marc/bibliographic/bd007g.html
 */
public class Tag007projected07 extends ControlSubfieldDefinition {
  private static Tag007projected07 uniqueInstance;

  private Tag007projected07() {
    initialize();
    extractValidCodes();
  }

  public static Tag007projected07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007projected07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Dimensions";
    id = "tag007projected07";
    mqTag = "dimensions";
    positionStart = 7;
    positionEnd = 8;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007g.html";
    codes = Utils.generateCodes(
      "a", "Standard 8 mm. film width",
      "b", "Super 8 mm./single 8 mm. film width",
      "c", "9.5 mm. film width",
      "d", "16 mm. film width",
      "e", "28 mm. film width",
      "f", "35 mm. film width",
      "g", "70 mm. film width",
      "j", "2x2 in. or 5x5 cm. slide",
      "k", "2 1/4 x 2 1/4 in. or 6x6 cm. slide",
      "s", "4x5 in. or 10x13 cm. transparency",
      "t", "5x7 in. or 13x18 cm. transparency",
      "v", "8x10 in. or 21x26 cm. transparency",
      "w", "9x9 in. or 23x23 cm. transparency",
      "x", "10x10 in. or 26x26 cm. transparency",
      "y", "7x7 in. or 18x18 cm. transparency",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "u", "7x7 in. or 18x18 cm. [OBSOLETE, 1980]",
      "y", "Unknown [OBSOLETE, 1980]"
    );
  }
}