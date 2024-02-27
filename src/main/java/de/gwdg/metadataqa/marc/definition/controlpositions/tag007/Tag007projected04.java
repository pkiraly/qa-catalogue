package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

/**
 * Base of emulsion
 * https://www.loc.gov/marc/bibliographic/bd007g.html
 */
public class Tag007projected04 extends ControlfieldPositionDefinition {
  private static Tag007projected04 uniqueInstance;

  private Tag007projected04() {
    initialize();
    extractValidCodes();
  }

  public static Tag007projected04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007projected04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Base of emulsion";
    id = "007projected04";
    mqTag = "baseOfEmulsion";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007g.html";
    codes = Utils.generateCodes(
      "d", "Glass",
      "e", "Synthetic",
      "j", "Safety film",
      "k", "Film base, other than safety film",
      "m", "Mixed collection",
      "o", "Paper",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
    historicalCodes = Utils.generateCodes(
      " ", "Not applicable or no attempt to code [OBSOLETE, 1980]",
      "n", "Not applicable [OBSOLETE, 1981]"
    );
  }
}