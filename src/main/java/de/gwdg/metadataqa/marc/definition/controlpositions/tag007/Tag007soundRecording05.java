package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Groove width/groove pitch
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording05 extends ControlfieldPositionDefinition {
  private static Tag007soundRecording05 uniqueInstance;

  private Tag007soundRecording05() {
    initialize();
  }

  public static Tag007soundRecording05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Groove width/groove pitch";
    id = "007soundRecording05";
    mqTag = "grooveWidthOrGroovePitch";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "m", "Microgroove/fine",
      "n", "Not applicable",
      "s", "Coarse/standard",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
  }
}