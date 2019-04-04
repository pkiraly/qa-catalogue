package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Encoding level
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader17 extends ControlSubfieldDefinition {
  private static Leader17 uniqueInstance;

  private Leader17() {
    initialize();
    extractValidCodes();
  }

  public static Leader17 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader17();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Encoding level";
    id = "leader17";
    mqTag = "encodingLevel";
    positionStart = 17;
    positionEnd = 18;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    codes = Utils.generateCodes(
      " ", "Full level",
      "1", "Full level, material not examined",
      "2", "Less-than-full level, material not examined",
      "3", "Abbreviated level",
      "4", "Core level",
      "5", "Partial (preliminary) level",
      "7", "Minimal level",
      "8", "Prepublication level",
      "u", "Unknown",
      "z", "Not applicable"
    );

    historicalCodes = Utils.generateCodes(
      "0", "Full level with item [OBSOLETE, 1997] [CAN/MARC only]",
      "6", "Minimal level [OBSOLETE, 1997] [CAN/MARC only]"
    );
  }
}