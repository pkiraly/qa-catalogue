package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007d.html
 */
public class Tag007globe00 extends ControlSubfieldDefinition {
  private static Tag007globe00 uniqueInstance;

  private Tag007globe00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007globe00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007globe00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007globe00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007d.html";
    codes = Utils.generateCodes(
      "d", "Globe"
    );
  }
}