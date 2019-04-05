package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007t.html
 */
public class Tag007text00 extends ControlSubfieldDefinition {
  private static Tag007text00 uniqueInstance;

  private Tag007text00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007text00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007text00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007text00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007t.html";
    codes = Utils.generateCodes(
      "t", "Text"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}