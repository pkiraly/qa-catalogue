package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007.html
 */
public class Tag007common00 extends ControlfieldPositionDefinition {
  private static Tag007common00 uniqueInstance;

  private Tag007common00() {
    initialize();

  }

  public static Tag007common00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007common00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "007common00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007.html";
    codes = Utils.generateCodes(
      "a", "Map",
      "c", "Electronic resource",
      "d", "Globe",
      "f", "Tactile material",
      "g", "Projected graphic",
      "h", "Microform",
      "k", "Nonprojected graphic",
      "m", "Motion picture",
      "o", "Kit",
      "q", "Notated music",
      "r", "Remote-sensing image",
      "s", "Sound recording",
      "t", "Text",
      "v", "Videorecording",
      "z", "Unspecified"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
  }
}