package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Special physical characteristics
 * https://www.loc.gov/marc/bibliographic/bd007f.html
 */
public class Tag007tactile09 extends ControlSubfieldDefinition {
  private static Tag007tactile09 uniqueInstance;

  private Tag007tactile09() {
    initialize();
    extractValidCodes();
  }

  public static Tag007tactile09 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007tactile09();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Special physical characteristics";
    id = "tag007tactile09";
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