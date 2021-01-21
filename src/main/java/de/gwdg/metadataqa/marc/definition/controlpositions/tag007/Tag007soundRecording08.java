package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Tape configuration
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording08 extends ControlfieldPositionDefinition {
  private static Tag007soundRecording08 uniqueInstance;

  private Tag007soundRecording08() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording08 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording08();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Tape configuration";
    id = "tag007soundRecording08";
    mqTag = "tapeConfiguration";
    positionStart = 8;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "a", "Full (1) track",
      "b", "Half (2) track",
      "c", "Quarter (4) track",
      "d", "Eight track",
      "e", "Twelve track",
      "f", "Sixteen track",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
  }
}