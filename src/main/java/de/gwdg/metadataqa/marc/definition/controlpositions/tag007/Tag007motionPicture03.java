package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Color
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture03 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture03 uniqueInstance;

  private Tag007motionPicture03() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture03 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture03();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Color";
    id = "tag007motionPicture03";
    mqTag = "color";
    positionStart = 3;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "b", "Black-and-white",
      "c", "Multicolored",
      "h", "Hand colored",
      "m", "Mixed",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}