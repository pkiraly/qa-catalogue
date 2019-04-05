package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Sound on medium or separate
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture05 extends ControlSubfieldDefinition {
  private static Tag007motionPicture05 uniqueInstance;

  private Tag007motionPicture05() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Sound on medium or separate";
    id = "tag007motionPicture05";
    mqTag = "soundOnMediumOrSeparate";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      " ", "No sound (silent)",
      "a", "Sound on medium",
      "b", "Sound separate from medium",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect, UseManage, UseOperate);
  }
}