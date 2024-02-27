package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Subfield code count
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader11 extends ControlfieldPositionDefinition {
  private static Leader11 uniqueInstance;

  private Leader11() {
    initialize();
    extractValidCodes();
  }

  public static Leader11 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader11();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Subfield code count";
    id = "leader11";
    mqTag = "subfieldCodeCount";
    positionStart = 11;
    positionEnd = 12;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    functions = Arrays.asList(ManagementProcess);
    codes = Utils.generateCodes(
      "2", "Number of character positions used for a subfield code"
    );
  }
}