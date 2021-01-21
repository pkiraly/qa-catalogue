package de.gwdg.metadataqa.marc.definition.controlpositions.leader;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Record length
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader00 extends ControlfieldPositionDefinition {
  private static Leader00 uniqueInstance;

  private Leader00() {
    initialize();
    extractValidCodes();
  }

  public static Leader00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Record length";
    id = "leader00";
    mqTag = "recordLength";
    positionStart = 0;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    functions = Arrays.asList(ManagementProcess);
  }
}