package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007q.html
 */
public class Tag007music01 extends ControlfieldPositionDefinition {
  private static Tag007music01 uniqueInstance;

  private Tag007music01() {
    initialize();
    extractValidCodes();
  }

  public static Tag007music01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007music01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "007music01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007q.html";
    codes = Utils.generateCodes(
      "u", "Unspecified",
      "|", "No attempt to code"
    );
  }
}