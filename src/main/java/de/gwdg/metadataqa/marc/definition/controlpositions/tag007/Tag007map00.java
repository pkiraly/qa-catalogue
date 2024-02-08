package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007a.html
 */
public class Tag007map00 extends ControlfieldPositionDefinition {
  private static Tag007map00 uniqueInstance;

  private Tag007map00() {
    initialize();

  }

  public static Tag007map00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007map00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "007map00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007a.html";
    codes = Utils.generateCodes(
      "a", "Map"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}