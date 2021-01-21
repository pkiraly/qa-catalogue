package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Antecedent/source
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro11 extends ControlfieldPositionDefinition {
  private static Tag007electro11 uniqueInstance;

  private Tag007electro11() {
    initialize();
    extractValidCodes();
  }

  public static Tag007electro11 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007electro11();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Antecedent/source";
    id = "tag007electro11";
    mqTag = "antecedentOrSource";
    positionStart = 11;
    positionEnd = 12;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
    codes = Utils.generateCodes(
      "a", "File reproduced from original",
      "b", "File reproduced from microform",
      "c", "File reproduced from an electronic resource",
      "d", "File reproduced from an intermediate (not microform)",
      "m", "Mixed",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    defaultCode = "|";
    functions = Arrays.asList(DiscoverySelect);
  }
}