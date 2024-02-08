package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Encoding level
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader17 extends ControlfieldPositionDefinition {
  private static Leader17 uniqueInstance;

  private Leader17() {
    initialize();
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
    functions = Arrays.asList(ManagementProcess);

    historicalCodes = Utils.generateCodes(
      "0", "Full level with item [OBSOLETE, 1997] [CAN/MARC only]",
      "6", "Minimal level [OBSOLETE, 1997] [CAN/MARC only]"
    );
  }
}