package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Special format characteristics
 * https://www.loc.gov/marc/bibliographic/bd008p.html
 */
public class Tag008map33 extends ControlfieldPositionDefinition {
  private static Tag008map33 uniqueInstance;

  private Tag008map33() {
    initialize();

  }

  public static Tag008map33 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008map33();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Special format characteristics";
    id = "008map33";
    mqTag = "specialFormatCharacteristics";
    positionStart = 33;
    positionEnd = 35;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008p.html";
    codes = Utils.generateCodes(
      " ", "No specified special format characteristics",
      "e", "Manuscript",
      "j", "Picture card, post card",
      "k", "Calendar",
      "l", "Puzzle",
      "n", "Game",
      "o", "Wall map",
      "p", "Playing cards",
      "r", "Loose-leaf",
      "z", "Other",
      "||", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "a", "Photocopy, blue line print [OBSOLETE, 1982]",
      "b", "Photocopy [OBSOLETE, 1982]",
      "c", "Negative photocopy [OBSOLETE, 1982]",
      "d", "Film negative [OBSOLETE, 1982]",
      "f", "Facsimile [OBSOLETE, 1982]",
      "g", "Relief model [OBSOLETE, 1982]",
      "h", "Rare [OBSOLETE, 1982]",
      "m", "Braille [OBSOLETE, 1998]",
      "q", "Large print [OBSOLETE, 1998]"
    );

    repeatableContent = true;
    unitLength = 1;
  }
}