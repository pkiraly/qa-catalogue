package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Length of the starting-character-position portion
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader21 extends ControlfieldPositionDefinition {
  private static Leader21 uniqueInstance;

  private Leader21() {
    initialize();
  }

  public static Leader21 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader21();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Length of the starting-character-position portion";
    id = "leader21";
    mqTag = "lengthOfTheStartingCharacterPositionPortion";
    positionStart = 21;
    positionEnd = 22;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    functions = Arrays.asList(ManagementProcess);
    codes = Utils.generateCodes(
      "5", "Number of characters in the starting-character-position portion of a Directory entry"
    );
  }
}