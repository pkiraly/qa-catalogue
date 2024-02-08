package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Government publication
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006visual11 extends ControlfieldPositionDefinition {
  private static Tag006visual11 uniqueInstance;

  private Tag006visual11() {
    initialize();
  }


  public static Tag006visual11 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006visual11();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Government publication";
    id = "006visual11";
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