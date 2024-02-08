package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007a.html
 */
public class Tag007map01 extends ControlfieldPositionDefinition {
  private static Tag007map01 uniqueInstance;

  private Tag007map01() {
    initialize();

  }

  public static Tag007map01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007map01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "007map01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007a.html";
    codes = Utils.generateCodes(
      "d", "Atlas",
      "g", "Diagram",
      "j", "Map",
      "k", "Profile",
      "q", "Model",
      "r", "Remote-sensing image",
      "s", "Section",
      "u", "Unspecified",
      "y", "View",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    historicalCodes = Utils.generateCodes(
      "a", "Aerial chart",
      "b", "Aerial remote-sensing image",
      "c", "Anamorphic map",
      "e", "Celestial chart",
      "f", "Chart",
      "h", "Hydrographic chart",
      "i", "Imaginative map",
      "j", "Orthophoto",
      "m", "Photo mosaic (controlled)",
      "n", "Photo mosaic (uncontrolled)",
      "o", "Photomap",
      "p", "Plan",
      "t", "Space remote-sensing image",
      "v", "Terrestrial remote-sensing image",
      "w", "Topographical drawing",
      "x", "Topographical print"
    );
  }
}