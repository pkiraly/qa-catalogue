package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008x.html
 */
public class Tag008mixed23 extends ControlfieldPositionDefinition {
  private static Tag008mixed23 uniqueInstance;

  private Tag008mixed23() {
    initialize();
    extractValidCodes();
  }

  public static Tag008mixed23 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008mixed23();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of item";
    id = "tag008mixed23";
    mqTag = "formOfItem";
    positionStart = 23;
    positionEnd = 24;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008x.html";
    codes = Utils.generateCodes(
      " ", "None of the following",
      "a", "Microfilm",
      "b", "Microfiche",
      "c", "Microopaque",
      "d", "Large print",
      "f", "Braille",
      "o", "Online",
      "q", "Direct electronic",
      "r", "Regular print reproduction",
      "s", "Electronic",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "g", "Punched paper tape [OBSOLETE, 1987]",
      "h", "Magnetic tape [OBSOLETE, 1987]",
      "i", "Multimedia [OBSOLETE, 1987]",
      "j", "Handwritten transcript [OBSOLETE, 1987]",
      "p", "Photocopy [OBSOLETE, 1987]",
      "t", "Typewritten transcript [OBSOLETE, 1987]",
      "z", "Other form of reproduction [OBSOLETE, 1987]"
    );
  }
}