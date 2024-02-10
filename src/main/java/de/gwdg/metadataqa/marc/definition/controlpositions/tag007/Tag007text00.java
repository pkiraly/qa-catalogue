package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007t.html
 */
public class Tag007text00 extends ControlfieldPositionDefinition {
  private static Tag007text00 uniqueInstance;

  private Tag007text00() {
    initialize();
  }

  public static Tag007text00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007text00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "007text00";
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