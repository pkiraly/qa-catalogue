package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Tape width
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording07 extends ControlSubfieldDefinition {
  private static Tag007soundRecording07 uniqueInstance;

  private Tag007soundRecording07() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Tape width";
    id = "tag007soundRecording07";
    mqTag = "tapeWidth";
    positionStart = 7;
    positionEnd = 8;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "l", "1/8 in.",
      "m", "1/4 in.",
      "n", "Not applicable",
      "o", "1/2 in.",
      "p", "1 in.",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
    historicalCodes = Utils.generateCodes(
      "a", "1/4 in. [OBSOLETE]",
      "b", "1/2 in. [OBSOLETE]",
      "c", "1 in. [OBSOLETE]"
    );
  }
}