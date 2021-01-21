package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Index
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book31 extends ControlfieldPositionDefinition {
  private static Tag008book31 uniqueInstance;

  private Tag008book31() {
    initialize();
    extractValidCodes();
  }

  public static Tag008book31 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008book31();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Index";
    id = "tag008book31";
    mqTag = "index";
    positionStart = 31;
    positionEnd = 32;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
    codes = Utils.generateCodes(
      "0", "No index",
      "1", "Index present",
      "|", "No attempt to code"
    );
  }
}