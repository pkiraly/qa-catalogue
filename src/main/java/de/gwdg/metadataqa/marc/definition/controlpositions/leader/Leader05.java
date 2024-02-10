package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Record status
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader05 extends ControlfieldPositionDefinition {
  private static Leader05 uniqueInstance;

  private Leader05() {
    initialize();
  }

  public static Leader05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Record status";
    id = "leader05";
    mqTag = "recordStatus";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    codes = Utils.generateCodes(
      "a", "Increase in encoding level",
      "c", "Corrected or revised",
      "d", "Deleted",
      "n", "New",
      "p", "Increase in encoding level from prepublication"
    );
    functions = Arrays.asList(ManagementProcess);
  }
}