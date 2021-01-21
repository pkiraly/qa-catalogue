package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Type of reproduction
 * https://www.loc.gov/marc/bibliographic/bd007d.html
 */
public class Tag007globe05 extends ControlfieldPositionDefinition {
  private static Tag007globe05 uniqueInstance;

  private Tag007globe05() {
    initialize();
    extractValidCodes();
  }

  public static Tag007globe05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007globe05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of reproduction";
    id = "tag007globe05";
    mqTag = "typeOfReproduction";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007d.html";
    codes = Utils.generateCodes(
      "f", "Facsimile",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}