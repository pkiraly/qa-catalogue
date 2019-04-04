package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Generation
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform11 extends ControlSubfieldDefinition {
  private static Tag007microform11 uniqueInstance;

  private Tag007microform11() {
    initialize();
    extractValidCodes();
  }

  public static Tag007microform11 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform11();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Generation";
    id = "tag007microform11";
    mqTag = "generation";
    positionStart = 11;
    positionEnd = 12;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    codes = Utils.generateCodes(
      "a", "First generation (master)",
      "b", "Printing master",
      "c", "Service copy",
      "m", "Mixed generation",
      "u", "Unknown",
      "|", "No attempt to code"
    );
  }
}