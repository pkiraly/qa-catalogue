package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

/**
 * Deterioration stage
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture15 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture15 uniqueInstance;

  private Tag007motionPicture15() {
    initialize();
  }

  public static Tag007motionPicture15 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture15();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Deterioration stage";
    id = "007motionPicture15";
    mqTag = "deteriorationStage";
    positionStart = 15;
    positionEnd = 16;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "a", "None apparent",
      "b", "Nitrate: suspicious odor",
      "c", "Nitrate: pungent odor",
      "d", "Nitrate: brownish, discoloration, fading, dusty",
      "e", "Nitrate: sticky",
      "f", "Nitrate: frothy, bubbles, blisters",
      "g", "Nitrate: congealed",
      "h", "Nitrate: powder",
      "k", "Non-nitrate: detectable deterioration",
      "l", "Non-nitrate: advanced deterioration",
      "m", "Non-nitrate: disaster",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect, UseManage);
  }
}