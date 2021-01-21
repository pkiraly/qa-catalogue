package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Government publication
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing11 extends ControlfieldPositionDefinition {
  private static Tag006continuing11 uniqueInstance;

  private Tag006continuing11() {
    initialize();
    extractValidCodes();
  }

  public static Tag006continuing11 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006continuing11();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Government publication";
    id = "tag006continuing11";
    mqTag = "governmentPublication";
    positionStart = 11;
    positionEnd = 12;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "Not a government publication",
      "a", "Autonomous or semi-autonomous component",
      "c", "Multilocal",
      "f", "Federal/national",
      "i", "International intergovernmental",
      "l", "Local",
      "m", "Multistate",
      "o", "Government publication-level undetermined",
      "s", "State, provincial, territorial, dependent, etc.",
      "u", "Unknown if item is government publication",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
  }
}