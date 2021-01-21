package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Generation
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture11 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture11 uniqueInstance;

  private Tag007motionPicture11() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture11 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture11();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Generation";
    id = "tag007motionPicture11";
    mqTag = "generation";
    positionStart = 11;
    positionEnd = 12;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "d", "Duplicate",
      "e", "Master",
      "o", "Original",
      "r", "Reference print/viewing copy",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
  }
}