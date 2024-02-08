package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Type of visual material
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006visual16 extends ControlfieldPositionDefinition {
  private static Tag006visual16 uniqueInstance;

  private Tag006visual16() {
    initialize();
  }


  public static Tag006visual16 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006visual16();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of visual material";
    id = "006visual16";
    mqTag = "typeOfVisualMaterial";
    positionStart = 16;
    positionEnd = 17;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "a", "Art original",
      "b", "Kit",
      "c", "Art reproduction",
      "d", "Diorama",
      "f", "Filmstrip",
      "g", "Game",
      "i", "Picture",
      "k", "Graphic",
      "l", "Technical drawing",
      "m", "Motion picture",
      "n", "Chart",
      "o", "Flash card",
      "p", "Microscope slide",
      "q", "Model",
      "r", "Realia",
      "s", "Slide",
      "t", "Transparency",
      "v", "Videorecording",
      "w", "Toy",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}