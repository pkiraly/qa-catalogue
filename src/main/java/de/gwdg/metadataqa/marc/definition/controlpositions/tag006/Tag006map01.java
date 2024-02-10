package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Form of material
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006map01 extends ControlfieldPositionDefinition {
  private static Tag006map01 uniqueInstance;

  private Tag006map01() {
    initialize();
  }

  public static Tag006map01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006map01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Relief";
    id = "006map01";
    mqTag = "relief";
    positionStart = 1;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "No relief shown",
      "a", "Contours",
      "b", "Shading",
      "c", "Gradient and bathymetric tints",
      "d", "Hachures",
      "e", "Bathymetry/soundings",
      "f", "Form lines",
      "g", "Spot heights",
      "i", "Pictorially",
      "j", "Land forms",
      "k", "Bathymetry/isolines",
      "m", "Rock drawings",
      "z", "Other",
      "|", "No attempt to code"
    );
    repeatableContent = true;
    unitLength = 1;
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}