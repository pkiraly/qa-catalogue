package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Type of computer file
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006computer09 extends ControlfieldPositionDefinition {
  private static Tag006computer09 uniqueInstance;

  private Tag006computer09() {
    initialize();
    extractValidCodes();
  }

  public static Tag006computer09 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006computer09();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of computer file";
    id = "tag006computer09";
    mqTag = "typeOfComputerFile";
    positionStart = 9;
    positionEnd = 10;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, UseOperate);
  }
}