package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007g.html
 */
public class Tag007projected00 extends ControlfieldPositionDefinition {
  private static Tag007projected00 uniqueInstance;

  private Tag007projected00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007projected00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007projected00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007projected00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007g.html";
    codes = Utils.generateCodes(
      "g", "Projected graphic"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
  }
}