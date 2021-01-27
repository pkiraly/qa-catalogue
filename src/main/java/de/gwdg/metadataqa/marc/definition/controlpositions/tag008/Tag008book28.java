package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Government publication
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book28 extends ControlfieldPositionDefinition {
  private static Tag008book28 uniqueInstance;

  private Tag008book28() {
    initialize();
    extractValidCodes();
  }

  public static Tag008book28 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008book28();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Government publication";
    id = "008book28";
    mqTag = "governmentPublication";
    positionStart = 28;
    positionEnd = 29;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
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
    historicalCodes = Utils.generateCodes(
      "n", "Government publication-level undetermined [OBSOLETE]"
    );
  }
}