package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * File formats
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro09 extends ControlSubfieldDefinition {
  private static Tag007electro09 uniqueInstance;

  private Tag007electro09() {
    initialize();
    extractValidCodes();
  }

  public static Tag007electro09 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007electro09();
    return uniqueInstance;
  }

  private void initialize() {
    label = "File formats";
    id = "tag007electro09";
    mqTag = "fileFormats";
    positionStart = 9;
    positionEnd = 10;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
    codes = Utils.generateCodes(
      "a", "One file format",
      "m", "Multiple file formats",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    defaultCode = "|";
  }
}