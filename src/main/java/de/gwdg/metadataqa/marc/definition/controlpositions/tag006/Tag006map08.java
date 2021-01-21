package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Type of cartographic material
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006map08 extends ControlfieldPositionDefinition {
  private static Tag006map08 uniqueInstance;

  private Tag006map08() {
    initialize();
    extractValidCodes();
  }

  public static Tag006map08 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006map08();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of cartographic material";
    id = "tag006map08";
    mqTag = "typeOfCartographicMaterial";
    positionStart = 8;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "a", "Single map",
      "b", "Map series",
      "c", "Map serial",
      "d", "Globe",
      "e", "Atlas",
      "f", "Separate supplement to another work",
      "g", "Bound as part of another work",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}