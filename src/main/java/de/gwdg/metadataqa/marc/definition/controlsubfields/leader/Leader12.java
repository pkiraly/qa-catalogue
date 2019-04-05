package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Base address of data
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader12 extends ControlSubfieldDefinition {
  private static Leader12 uniqueInstance;

  private Leader12() {
    initialize();
    extractValidCodes();
  }

  public static Leader12 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader12();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Base address of data";
    id = "leader12";
    mqTag = "baseAddressOfData";
    positionStart = 12;
    positionEnd = 17;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    functions = Arrays.asList(ManagementProcess);
  }
}