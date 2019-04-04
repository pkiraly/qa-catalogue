package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Music parts
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006music04 extends ControlSubfieldDefinition {
  private static Tag006music04 uniqueInstance;

  private Tag006music04() {
    initialize();
    extractValidCodes();
  }

  public static Tag006music04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006music04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Music parts";
    id = "tag006music04";
    mqTag = "musicParts";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "No parts in hand or not specified",
      "d", "Instrumental and vocal parts",
      "e", "Instrumental parts",
      "f", "Vocal parts",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );
  }
}