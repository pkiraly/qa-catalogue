package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Original capture and storage technique
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording13 extends ControlfieldPositionDefinition {
  private static Tag007soundRecording13 uniqueInstance;

  private Tag007soundRecording13() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording13 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording13();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Original capture and storage technique";
    id = "007soundRecording13";
    mqTag = "captureAndStorageTechnique";
    positionStart = 13;
    positionEnd = 14;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "a", "Acoustical capture, analog direct storage",
      "b", "Electrical capture, analog direct storage",
      "d", "Electrical capture, digital storage",
      "e", "Electrical capture, analog electrical storage",
      "u", "Unknown capture and storage",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect, UseManage, UseOperate);
  }
}