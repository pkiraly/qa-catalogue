package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Subfield code count
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader11 extends ControlSubfieldDefinition {
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
  }
}