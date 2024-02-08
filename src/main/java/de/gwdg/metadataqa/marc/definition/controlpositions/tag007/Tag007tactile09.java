package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Special physical characteristics
 * https://www.loc.gov/marc/bibliographic/bd007f.html
 */
public class Tag007tactile09 extends ControlfieldPositionDefinition {
  private static Tag007tactile09 uniqueInstance;

  private Tag007tactile09() {
    initialize();

  }

  public static Tag007tactile09 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007tactile09();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Special physical characteristics";
    id = "007tactile09";
    mqTag = "specialPhysicalCharacteristics";
    positionStart = 9;
    positionEnd = 10;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007f.html";
    codes = Utils.generateCodes(
      "a", "Print/braille",
      "b", "Jumbo or enlarged braille",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}