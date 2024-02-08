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
 * https://www.loc.gov/marc/bibliographic/bd007v.html
 */
public class Tag007video07 extends ControlfieldPositionDefinition {
  private static Tag007video07 uniqueInstance;

  private Tag007video07() {
    initialize();
  }


  public static Tag007video07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007video07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Dimensions";
    id = "007video07";
    mqTag = "dimensions";
    positionStart = 7;
    positionEnd = 8;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007v.html";
    codes = Utils.generateCodes(
      "a", "8 mm.",
      "m", "1/4 in.",
      "o", "1/2 in.",
      "p", "1 in.",
      "q", "2 in.",
      "r", "3/4 in.",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
    historicalCodes = Utils.generateCodes(
      "n", "1/4 in. [OBSOLETE, 1981]"
    );
  }
}