package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Conference publication
 * same as 008/29
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book12 extends ControlSubfieldDefinition {
  private static Tag006book12 uniqueInstance;

  private Tag006book12() {
    initialize();
    extractValidCodes();
  }

  public static Tag006book12 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006book12();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Conference publication";
    id = "tag006book12";
    mqTag = "conferencePublication";
    positionStart = 12;
    positionEnd = 13;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "0", "Not a conference publication",
      "1", "Conference publication",
      "|", "No attempt to code"
    );
  }
}