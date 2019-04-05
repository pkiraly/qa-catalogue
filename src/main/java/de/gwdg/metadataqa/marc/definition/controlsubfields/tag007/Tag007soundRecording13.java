package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Capture and storage technique
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording13 extends ControlSubfieldDefinition {
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
    label = "Capture and storage technique";
    id = "tag007soundRecording13";
    mqTag = "captureAndStorageTechnique";
    positionStart = 13;
    positionEnd = 14;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "a", "Acoustical capture, direct storage",
      "b", "Direct storage, not acoustical",
      "d", "Digital storage",
      "e", "Analog electrical storage",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect, UsageManage, UsageOperate);
  }
}