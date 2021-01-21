package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Illustrations
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book18 extends ControlfieldPositionDefinition {
  private static Tag008book18 uniqueInstance;

  private Tag008book18() {
    initialize();
    extractValidCodes();
  }

  public static Tag008book18 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008book18();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Illustrations";
    id = "tag008book18";
    mqTag = "illustrations";
    positionStart = 18;
    positionEnd = 22;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
    codes = Utils.generateCodes(
      " ", "No illustrations",
      "a", "Illustrations",
      "b", "Maps",
      "c", "Portraits",
      "d", "Charts",
      "e", "Plans",
      "f", "Plates",
      "g", "Music",
      "h", "Facsimiles",
      "i", "Coats of arms",
      "j", "Genealogical tables",
      "k", "Forms",
      "l", "Samples",
      "m", "Phonodisc, phonowire, etc.",
      "o", "Photographs",
      "p", "Illuminations",
      "|", "No attempt to code"
    );

    repeatableContent = true;
    unitLength = 1;
  }
}