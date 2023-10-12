package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

/**
 * Frequency
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing01 extends ControlfieldPositionDefinition {
  private static Tag006continuing01 uniqueInstance;

  private Tag006continuing01() {
    initialize();
    extractValidCodes();
  }

  public static Tag006continuing01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006continuing01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Frequency";
    id = "006continuing01";
    mqTag = "frequency";
    positionStart = 1;
    positionEnd = 2;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "No determinable frequency",
      "a", "Annual",
      "b", "Bimonthly",
      "c", "Semiweekly",
      "d", "Daily",
      "e", "Biweekly",
      "f", "Semiannual",
      "g", "Biennial",
      "h", "Triennial",
      "i", "Three times a week",
      "j", "Three times a month",
      "k", "Continuously updated",
      "m", "Monthly",
      "q", "Quarterly",
      "s", "Semimonthly",
      "t", "Three times a year",
      "u", "Unknown",
      "w", "Weekly",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, UseManage);
  }
}