package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Entry convention
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing34 extends ControlfieldPositionDefinition {
  private static Tag008continuing34 uniqueInstance;

  private Tag008continuing34() {
    initialize();
    extractValidCodes();
  }

  public static Tag008continuing34 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008continuing34();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Entry convention";
    id = "tag008continuing34";
    mqTag = "entryConvention";
    positionStart = 34;
    positionEnd = 35;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
    codes = Utils.generateCodes(
      "0", "Successive entry",
      "1", "Latest entry",
      "2", "Integrated entry",
      "|", "No attempt to code"
    );
  }
}