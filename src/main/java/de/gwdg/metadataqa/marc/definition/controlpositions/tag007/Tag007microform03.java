package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Positive/negative aspect
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform03 extends ControlfieldPositionDefinition {
  private static Tag007microform03 uniqueInstance;

  private Tag007microform03() {
    initialize();
    extractValidCodes();
  }

  public static Tag007microform03 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform03();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Positive/negative aspect";
    id = "007microform03";
    mqTag = "positiveNegativeAspect";
    positionStart = 3;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    codes = Utils.generateCodes(
      "a", "Positive",
      "b", "Negative",
      "m", "Mixed polarity",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
  }
}