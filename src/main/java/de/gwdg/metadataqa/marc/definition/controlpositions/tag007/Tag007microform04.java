package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Dimensions
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform04 extends ControlfieldPositionDefinition {
  private static Tag007microform04 uniqueInstance;

  private Tag007microform04() {
    initialize();
  }


  public static Tag007microform04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Dimensions";
    id = "007microform04";
    mqTag = "dimensions";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    codes = Utils.generateCodes(
      "a", "8 mm.",
      "d", "16 mm.",
      "f", "35 mm.",
      "g", "70 mm.",
      "h", "105 mm.",
      "l", "3x5 in. or 8x13 cm.",
      "m", "4x6 in. or 11x15 cm.",
      "o", "6x9 in. or 16x23 cm.",
      "p", "3 1/4 x 7 3/8 in. or 9x19 cm.",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
  }
}