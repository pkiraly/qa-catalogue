package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007v.html
 */
public class Tag007video00 extends ControlSubfieldDefinition {
  private static Tag007video00 uniqueInstance;

  private Tag007video00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007video00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007video00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007video00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007v.html";
    codes = Utils.generateCodes(
      "v", "Videorecording"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UsageManage, UsageOperate);
  }
}