package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007o.html
 */
public class Tag007kit00 extends ControlSubfieldDefinition {
  private static Tag007kit00 uniqueInstance;

  private Tag007kit00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007kit00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007kit00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007kit00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007o.html";
    codes = Utils.generateCodes(
      "o", "Kit"
    );
  }
}