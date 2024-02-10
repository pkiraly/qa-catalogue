package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Indicator count
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader10 extends ControlfieldPositionDefinition {
  private static Leader10 uniqueInstance;

  private Leader10() {
    initialize();
  }

  public static Leader10 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader10();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Indicator count";
    id = "leader10";
    mqTag = "indicatorCount";
    positionStart = 10;
    positionEnd = 11;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    functions = Arrays.asList(ManagementProcess);
    codes = Utils.generateCodes(
      "2", "Number of character positions used for indicators"
    );
  }
}