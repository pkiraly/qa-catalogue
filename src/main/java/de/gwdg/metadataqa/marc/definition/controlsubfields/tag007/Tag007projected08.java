package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Secondary support material
 * https://www.loc.gov/marc/bibliographic/bd007g.html
 */
public class Tag007projected08 extends ControlSubfieldDefinition {
  private static Tag007projected08 uniqueInstance;

  private Tag007projected08() {
    initialize();
    extractValidCodes();
  }

  public static Tag007projected08 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007projected08();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Secondary support material";
    id = "tag007projected08";
    mqTag = "secondarySupportMaterial";
    positionStart = 8;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007g.html";
    codes = Utils.generateCodes(
      " ", "No secondary support",
      "c", "Cardboard",
      "d", "Glass",
      "e", "Synthetic",
      "h", "Metal",
      "j", "Metal and glass",
      "k", "Synthetic and glass",
      "m", "Mixed collection",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UsageManage);
  }
}