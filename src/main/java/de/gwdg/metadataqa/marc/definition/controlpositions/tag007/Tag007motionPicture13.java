package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Refined categories of color
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture13 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture13 uniqueInstance;

  private Tag007motionPicture13() {
    initialize();
  }

  public static Tag007motionPicture13 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture13();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Refined categories of color";
    id = "007motionPicture13";
    mqTag = "refinedCategoriesOfColor";
    positionStart = 13;
    positionEnd = 14;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "a", "3 layer color",
      "b", "2 color, single strip",
      "c", "Undetermined 2 color",
      "d", "Undetermined 3 color",
      "e", "3 strip color",
      "f", "2 strip color",
      "g", "Red strip",
      "h", "Blue or green strip",
      "i", "Cyan strip",
      "j", "Magenta strip",
      "k", "Yellow strip",
      "l", "S E N 2",
      "m", "S E N 3",
      "n", "Not applicable",
      "p", "Sepia tone",
      "q", "Other tone",
      "r", "Tint",
      "s", "Tinted and toned",
      "t", "Stencil color",
      "u", "Unknown",
      "v", "Hand colored",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}