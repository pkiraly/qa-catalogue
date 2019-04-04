package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Index
 * same as 008/31
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book14 extends ControlSubfieldDefinition {
  private static Tag006book14 uniqueInstance;

  private Tag006book14() {
    initialize();
    extractValidCodes();
  }

  public static Tag006book14 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006book14();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Index";
    id = "tag006book14";
    mqTag = "index";
    positionStart = 14;
    positionEnd = 15;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "0", "No index",
      "1", "Index present",
      "|", "No attempt to code"
    );
  }
}