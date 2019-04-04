package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Transposition and arrangement
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music33 extends ControlSubfieldDefinition {
  private static Tag008music33 uniqueInstance;

  private Tag008music33() {
    initialize();
    extractValidCodes();
  }

  public static Tag008music33 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008music33();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Transposition and arrangement";
    id = "tag008music33";
    mqTag = "transpositionAndArrangement";
    positionStart = 33;
    positionEnd = 34;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
    codes = Utils.generateCodes(
      " ", "Not arrangement or transposition or not specified",
      "a", "Transposition",
      "b", "Arrangement",
      "c", "Both transposed and arranged",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );
  }
}