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
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform01 extends ControlfieldPositionDefinition {
  private static Tag007microform01 uniqueInstance;

  private Tag007microform01() {
    initialize();
  }

  public static Tag007microform01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Specific material designation";
    id = "007microform01";
    mqTag = "specificMaterialDesignation";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    codes = Utils.generateCodes(
      "a", "Aperture card",
      "b", "Microfilm cartridge",
      "c", "Microfilm cassette",
      "d", "Microfilm reel",
      "e", "Microfiche",
      "f", "Microfiche cassette",
      "g", "Microopaque",
      "h", "Microfilm slip",
      "j", "Microfilm roll",
      "u", "Unspecified",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate);
  }
}