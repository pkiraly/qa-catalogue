package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Index
 * https://www.loc.gov/marc/bibliographic/bd008p.html
 */
public class Tag008map31 extends ControlfieldPositionDefinition {
  private static Tag008map31 uniqueInstance;

  private Tag008map31() {
    initialize();
  }

  public static Tag008map31 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008map31();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Index";
    id = "008map31";
    mqTag = "index";
    positionStart = 31;
    positionEnd = 32;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008p.html";
    codes = Utils.generateCodes(
      "0", "No index",
      "1", "Index present",
      "|", "No attempt to code"
    );
  }
}