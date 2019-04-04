package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Generation
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture11 extends ControlSubfieldDefinition {
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
  }
}