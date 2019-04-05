package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007f.html
 */
public class Tag007tactile00 extends ControlSubfieldDefinition {
  private static Tag007tactile00 uniqueInstance;

  private Tag007tactile00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007tactile00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007tactile00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007tactile00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007f.html";
    codes = Utils.generateCodes(
      "f", "Tactile material"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}