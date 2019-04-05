package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording01 extends ControlSubfieldDefinition {
  private static Tag007soundRecording01 uniqueInstance;

  private Tag007soundRecording01() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "tag007soundRecording01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "d", "Sound disc",
      "e", "Cylinder",
      "g", "Sound cartridge",
      "i", "Sound-track film",
      "q", "Roll",
      "r", "Remote",
      "s", "Sound cassette",
      "t", "Sound-tape reel",
      "u", "Unspecified",
      "w", "Wire recording",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
    historicalCodes = Utils.generateCodes(
      "c", "Cylinder [OBSOLETE]",
      "f", "Sound-track film [OBSOLETE]",
      "r", "Roll [OBSOLETE]"
    );
  }
}