package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007d.html
 */
public class Tag007globe00 extends ControlfieldPositionDefinition {
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
    id = "007globe00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007d.html";
    codes = Utils.generateCodes(
      "d", "Globe"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}