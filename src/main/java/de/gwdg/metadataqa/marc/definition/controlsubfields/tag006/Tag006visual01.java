package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Running time for motion pictures and videorecordings
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006visual01 extends ControlSubfieldDefinition {
  private static Tag006visual01 uniqueInstance;

  private Tag006visual01() {
    initialize();
    extractValidCodes();
  }

  public static Tag006visual01 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006visual01();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Running time for motion pictures and videorecordings";
    id = "tag006visual01";
    mqTag = "runningTime";
    positionStart = 1;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "000", "Running time exceeds three characters",
      "001-999", "Running time",
      "nnn", "Not applicable",
      "---", "Unknown",
      "|||", "No attempt to code"
    );
    getCode("001-999").setRange(true);
  }
}