package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007z.html
 */
public class Tag007unspecified00 extends ControlSubfieldDefinition {
  private static Tag007unspecified00 uniqueInstance;

  private Tag007unspecified00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007unspecified00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007unspecified00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007unspecified00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007z.html";
    codes = Utils.generateCodes(
      "z", "Unspecified"
    );
  }
}