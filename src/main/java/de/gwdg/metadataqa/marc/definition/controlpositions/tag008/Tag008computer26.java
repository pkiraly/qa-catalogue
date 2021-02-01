package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Type of computer file
 * https://www.loc.gov/marc/bibliographic/bd008c.html
 */
public class Tag008computer26 extends ControlfieldPositionDefinition {
  private static Tag008computer26 uniqueInstance;

  private Tag008computer26() {
    initialize();
    extractValidCodes();
  }

  public static Tag008computer26 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008computer26();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of computer file";
    id = "008computer26";
    mqTag = "typeOfComputerFile";
    positionStart = 26;
    positionEnd = 27;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008c.html";
    codes = Utils.generateCodes(
      "a", "Numeric data",
      "b", "Computer program",
      "c", "Representational",
      "d", "Document",
      "e", "Bibliographic data",
      "f", "Font",
      "g", "Game",
      "h", "Sound",
      "i", "Interactive multimedia",
      "j", "Online system or service",
      "m", "Combination",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
  }
}