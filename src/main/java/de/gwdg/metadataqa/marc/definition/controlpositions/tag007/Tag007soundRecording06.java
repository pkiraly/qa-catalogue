package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Dimensions
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording06 extends ControlfieldPositionDefinition {
  private static Tag007soundRecording06 uniqueInstance;

  private Tag007soundRecording06() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Dimensions";
    id = "tag007soundRecording06";
    mqTag = "dimensions";
    positionStart = 6;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "a", "3 in. diameter",
      "b", "5 in. diameter",
      "c", "7 in. diameter",
      "d", "10 in. diameter",
      "e", "12 in. diameter",
      "f", "16 in. diameter",
      "g", "4 3/4 in. or 12 cm. diameter",
      "j", "3 7/8 x 2 1/2 in.",
      "n", "Not applicable",
      "o", "5 1/4 x 3 7/8 in.",
      "s", "2 3/4 x 4 in.",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
  }
}