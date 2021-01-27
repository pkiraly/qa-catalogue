package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture00 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture00 uniqueInstance;

  private Tag007motionPicture00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "007motionPicture00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "m", "Motion picture"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
  }
}