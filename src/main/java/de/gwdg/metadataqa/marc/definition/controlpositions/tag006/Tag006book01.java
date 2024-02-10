package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Illustrations
 * same as 008/18-21
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book01 extends ControlfieldPositionDefinition {
  private static Tag006book01 uniqueInstance;

  private Tag006book01() {
    initialize();
  }

  public static Tag006book01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006book01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Illustrations";
    id = "006book01";
    mqTag = "illustrations";
    positionStart = 1;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
    functions = Arrays.asList(DiscoverySelect);
  }
}