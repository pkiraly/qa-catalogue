package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Transposition and arrangement
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006music16 extends ControlfieldPositionDefinition {
  private static Tag006music16 uniqueInstance;

  private Tag006music16() {
    initialize();
    extractValidCodes();
  }

  public static Tag006music16 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006music16();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Transposition and arrangement";
    id = "006music16";
    mqTag = "transpositionAndArrangement";
    positionStart = 16;
    positionEnd = 17;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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