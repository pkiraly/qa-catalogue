package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing01 extends ControlSubfieldDefinition {
  private static Tag007remoteSensing01 uniqueInstance;

  private Tag007remoteSensing01() {
    initialize();
    extractValidCodes();
  }

  public static Tag007remoteSensing01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "tag007remoteSensing01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "u", "Unspecified",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      " ", "No type specified [OBSOLETE, 1998]"
    );
  }
}