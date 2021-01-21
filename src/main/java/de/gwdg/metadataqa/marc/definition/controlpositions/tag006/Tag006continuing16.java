package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Original alphabet or script of title
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing16 extends ControlfieldPositionDefinition {
  private static Tag006continuing16 uniqueInstance;

  private Tag006continuing16() {
    initialize();
    extractValidCodes();
  }

  public static Tag006continuing16 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006continuing16();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Original alphabet or script of title";
    id = "tag006continuing16";
    mqTag = "originalAlphabetOrScriptOfTitle";
    positionStart = 16;
    positionEnd = 17;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "No alphabet or script given/No key title",
      "a", "Basic Roman",
      "b", "Extended Roman",
      "c", "Cyrillic",
      "d", "Japanese",
      "e", "Chinese",
      "f", "Arabic",
      "g", "Greek",
      "h", "Hebrew",
      "i", "Thai",
      "j", "Devanagari",
      "k", "Korean",
      "l", "Tamil",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
  }
}