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
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform00 extends ControlfieldPositionDefinition {
  private static Tag007microform00 uniqueInstance;

  private Tag007microform00() {
    initialize();
  }


  public static Tag007microform00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "007microform00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    codes = Utils.generateCodes(
      "h", "Microform"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
  }
}