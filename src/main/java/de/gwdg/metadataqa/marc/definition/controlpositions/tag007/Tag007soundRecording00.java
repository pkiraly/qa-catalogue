package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording00 extends ControlfieldPositionDefinition {
  private static Tag007soundRecording00 uniqueInstance;

  private Tag007soundRecording00() {
    initialize();
  }

  public static Tag007soundRecording00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "007soundRecording00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "s", "Sound recording"
    );
    Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}