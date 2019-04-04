package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Platform construction type
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing06 extends ControlSubfieldDefinition {
  private static Tag007remoteSensing06 uniqueInstance;

  private Tag007remoteSensing06() {
    initialize();
    extractValidCodes();
  }

  public static Tag007remoteSensing06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Platform construction type";
    id = "tag007remoteSensing06";
    mqTag = "platformConstructionType";
    positionStart = 6;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "a", "Balloon",
      "b", "Aircraft--low altitude",
      "c", "Aircraft--medium altitude",
      "d", "Aircraft--high altitude",
      "e", "Manned spacecraft",
      "f", "Unmanned spacecraft",
      "g", "Land-based remote-sensing device",
      "h", "Water surface-based remote-sensing device",
      "i", "Submersible remote-sensing device",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
  }
}