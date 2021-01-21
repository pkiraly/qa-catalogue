package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007o.html
 */
public class Tag007kit01 extends ControlfieldPositionDefinition {
  private static Tag007kit01 uniqueInstance;

  private Tag007kit01() {
    initialize();
    extractValidCodes();
  }

  public static Tag007kit01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007kit01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "tag007kit01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007o.html";
    codes = Utils.generateCodes(
      "u", "Unspecified",
      "|", "No attempt to code"
    );
  }
}