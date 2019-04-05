package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Special playback characteristics
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording12 extends ControlSubfieldDefinition {
  private static Tag007soundRecording12 uniqueInstance;

  private Tag007soundRecording12() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording12 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording12();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Special playback characteristics";
    id = "tag007soundRecording12";
    mqTag = "specialPlaybackCharacteristics";
    positionStart = 12;
    positionEnd = 13;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "a", "NAB standard",
      "b", "CCIR standard",
      "c", "Dolby-B encoded",
      "d", "dbx encoded",
      "e", "Digital recording",
      "f", "Dolby-A encoded",
      "g", "Dolby-C encoded",
      "h", "CX encoded",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UsageOperate);
  }
}