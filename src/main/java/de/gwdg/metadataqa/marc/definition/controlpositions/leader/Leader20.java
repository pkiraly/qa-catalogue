package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Length of the length-of-field portion
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader20 extends ControlfieldPositionDefinition {
  private static Leader20 uniqueInstance;

  private Leader20() {
    initialize();
  }

  public static Leader20 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader20();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Length of the length-of-field portion";
    id = "leader20";
    mqTag = "lengthOfTheLengthOfFieldPortion";
    positionStart = 20;
    positionEnd = 21;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    functions = Arrays.asList(ManagementProcess);
    codes = Utils.generateCodes(
      "4", "Number of characters in the length-of-field portion of a Directory entry"
    );
  }
}