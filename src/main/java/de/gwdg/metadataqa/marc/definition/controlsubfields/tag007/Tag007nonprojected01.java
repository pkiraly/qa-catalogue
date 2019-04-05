package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007k.html
 */
public class Tag007nonprojected01 extends ControlSubfieldDefinition {
  private static Tag007nonprojected01 uniqueInstance;

  private Tag007nonprojected01() {
    initialize();
    extractValidCodes();
  }

  public static Tag007nonprojected01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007nonprojected01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "tag007nonprojected01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007k.html";
    codes = Utils.generateCodes(
      "a", "Activity card",
      "c", "Collage",
      "d", "Drawing",
      "e", "Painting",
      "f", "Photomechanical print",
      "g", "Photonegative",
      "h", "Photoprint",
      "i", "Picture",
      "j", "Print",
      "k", "Poster",
      "l", "Technical drawing",
      "n", "Chart",
      "o", "Flash card",
      "p", "Postcard",
      "q", "Icon",
      "r", "Radiograph",
      "s", "Study print",
      "u", "Unspecified",
      "v", "Photograph, type unspecified",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UsageManage);
  }
}