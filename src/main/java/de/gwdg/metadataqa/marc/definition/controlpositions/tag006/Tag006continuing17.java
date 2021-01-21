package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Entry convention
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing17 extends ControlfieldPositionDefinition {
  private static Tag006continuing17 uniqueInstance;

  private Tag006continuing17() {
    initialize();
    extractValidCodes();
  }

  public static Tag006continuing17 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006continuing17();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Entry convention";
    id = "tag006continuing17";
    mqTag = "entryConvention";
    positionStart = 17;
    positionEnd = 18;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "0", "Successive entry",
      "1", "Latest entry",
      "2", "Integrated entry",
      "|", "No attempt to code"
    );
  }
}