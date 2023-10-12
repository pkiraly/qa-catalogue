package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Conference publication
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing12 extends ControlfieldPositionDefinition {
  private static Tag006continuing12 uniqueInstance;

  private Tag006continuing12() {
    initialize();
    extractValidCodes();
  }

  public static Tag006continuing12 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006continuing12();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Conference publication";
    id = "006continuing12";
    mqTag = "conferencePublication";
    positionStart = 12;
    positionEnd = 13;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "0", "Not a conference publication",
      "1", "Conference publication",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
  }
}