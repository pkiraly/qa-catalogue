package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Form of item
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing23 extends ControlfieldPositionDefinition {
  private static Tag008continuing23 uniqueInstance;

  private Tag008continuing23() {
    initialize();
    extractValidCodes();
  }

  public static Tag008continuing23 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008continuing23();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Form of item";
    id = "008continuing23";
    mqTag = "formOfItem";
    positionStart = 23;
    positionEnd = 24;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
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
      "z", "Other [OBSOLETE, 1987]"
    );
  }
}