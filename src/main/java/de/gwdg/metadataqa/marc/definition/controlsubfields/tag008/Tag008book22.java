package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Target audience
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book22 extends ControlSubfieldDefinition {
  private static Tag008book22 uniqueInstance;

  private Tag008book22() {
    initialize();
    extractValidCodes();
  }

  public static Tag008book22 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008book22();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Target audience";
    id = "tag008book22";
    mqTag = "targetAudience";
    positionStart = 22;
    positionEnd = 23;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
    codes = Utils.generateCodes(
      " ", "Unknown or not specified",
      "a", "Preschool",
      "b", "Primary",
      "c", "Pre-adolescent",
      "d", "Adolescent",
      "e", "Adult",
      "f", "Specialized",
      "g", "General",
      "j", "Juvenile",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "u", "School material at first level [OBSOLETE]",
      "v", "School material at second level [OBSOLETE]"
    );
  }
}