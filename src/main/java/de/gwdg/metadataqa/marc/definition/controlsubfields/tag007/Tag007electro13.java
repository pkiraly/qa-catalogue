package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Reformatting quality
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro13 extends ControlSubfieldDefinition {
  private static Tag007electro13 uniqueInstance;

  private Tag007electro13() {
    initialize();
    extractValidCodes();
  }

  public static Tag007electro13 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007electro13();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Reformatting quality";
    id = "tag007electro13";
    mqTag = "reformattingQuality";
    positionStart = 13;
    positionEnd = 14;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
    codes = Utils.generateCodes(
      "a", "Access",
      "n", "Not applicable",
      "p", "Preservation",
      "r", "Replacement",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    defaultCode = "|";
  }
}