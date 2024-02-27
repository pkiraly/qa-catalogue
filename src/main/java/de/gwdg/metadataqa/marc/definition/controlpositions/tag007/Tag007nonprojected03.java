package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Color
 * https://www.loc.gov/marc/bibliographic/bd007k.html
 */
public class Tag007nonprojected03 extends ControlfieldPositionDefinition {
  private static Tag007nonprojected03 uniqueInstance;

  private Tag007nonprojected03() {
    initialize();
    extractValidCodes();
  }

  public static Tag007nonprojected03 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007nonprojected03();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Color";
    id = "007nonprojected03";
    mqTag = "color";
    positionStart = 3;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007k.html";
    codes = Utils.generateCodes(
      "a", "One color",
      "b", "Black-and-white",
      "c", "Multicolored",
      "h", "Hand colored",
      "m", "Mixed",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}