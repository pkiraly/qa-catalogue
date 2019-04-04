package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Music parts
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music21 extends ControlSubfieldDefinition {
  private static Tag008music21 uniqueInstance;

  private Tag008music21() {
    initialize();
    extractValidCodes();
  }

  public static Tag008music21 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008music21();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Music parts";
    id = "tag008music21";
    mqTag = "musicParts";
    positionStart = 21;
    positionEnd = 22;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
    codes = Utils.generateCodes(
      " ", "No parts in hand or not specified",
      "d", "Instrumental and vocal parts",
      "e", "Instrumental parts",
      "f", "Vocal parts",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );

    historicalCodes = Utils.generateCodes(
      "a", "Parts exist"
    );
  }
}