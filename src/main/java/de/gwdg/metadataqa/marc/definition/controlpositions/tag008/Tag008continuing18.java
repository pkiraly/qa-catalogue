package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Frequency
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing18 extends ControlfieldPositionDefinition {
  private static Tag008continuing18 uniqueInstance;

  private Tag008continuing18() {
    initialize();
  }


  public static Tag008continuing18 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008continuing18();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Frequency";
    id = "008continuing18";
    mqTag = "frequency";
    positionStart = 18;
    positionEnd = 19;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
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
  }
}