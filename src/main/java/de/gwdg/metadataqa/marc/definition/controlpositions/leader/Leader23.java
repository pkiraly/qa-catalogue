package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Length of the implementation-defined portion
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader23 extends ControlfieldPositionDefinition {
  private static Leader23 uniqueInstance;

  private Leader23() {
    initialize();

  }

  public static Leader23 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader23();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Undefined";
    id = "leader23";
    mqTag = "undefined";
    positionStart = 23;
    positionEnd = 24;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    functions = Arrays.asList(ManagementProcess);
    codes = Utils.generateCodes(
      "0", "Undefined"
    );
  }
}