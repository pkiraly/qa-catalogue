package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Level of contraction
 * https://www.loc.gov/marc/bibliographic/bd007f.html
 */
public class Tag007tactile05 extends ControlfieldPositionDefinition {
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
    id = "007tactile05";
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
    functions = Arrays.asList(DiscoverySelect);
  }
}