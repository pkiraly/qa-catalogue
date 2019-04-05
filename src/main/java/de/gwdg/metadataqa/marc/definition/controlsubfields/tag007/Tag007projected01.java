package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007g.html
 */
public class Tag007projected01 extends ControlSubfieldDefinition {
  private static Tag007projected01 uniqueInstance;

  private Tag007projected01() {
    initialize();
    extractValidCodes();
  }

  public static Tag007projected01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007projected01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "tag007projected01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007g.html";
    codes = Utils.generateCodes(
      "c", "Filmstrip cartridge",
      "d", "Filmslip",
      "f", "Filmstrip, type unspecified",
      "o", "Filmstrip roll",
      "s", "Slide",
      "t", "Transparency",
      "u", "Unspecified",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UsageManage, UsageOperate);
    historicalCodes = Utils.generateCodes(
      " ", "Not applicable or no attempt to code [OBSOLETE, 1980]",
      "n", "Not applicable [OBSOLETE, 1981] [USMARC only]"
    );
  }
}