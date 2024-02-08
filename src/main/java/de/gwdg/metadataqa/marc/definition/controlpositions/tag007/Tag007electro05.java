package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Sound
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro05 extends ControlfieldPositionDefinition {
  private static Tag007electro05 uniqueInstance;

  private Tag007electro05() {
    initialize();

  }

  public static Tag007electro05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007electro05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Sound";
    id = "007electro05";
    mqTag = "sound";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
    codes = Utils.generateCodes(
      " ", "No sound (silent)",
      "a", "Sound",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}