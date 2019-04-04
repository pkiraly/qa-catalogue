package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Kind of cutting
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording11 extends ControlSubfieldDefinition {
  private static Tag007soundRecording11 uniqueInstance;

  private Tag007soundRecording11() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording11 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording11();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Kind of cutting";
    id = "tag007soundRecording11";
    mqTag = "kindOfCutting";
    positionStart = 11;
    positionEnd = 12;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "h", "Hill-and-dale cutting",
      "l", "Lateral or combined cutting",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );
  }
}