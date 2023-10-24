package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Positive/negative aspect
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture10 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture10 uniqueInstance;

  private Tag007motionPicture10() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture10 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture10();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Positive/negative aspect";
    id = "007motionPicture10";
    mqTag = "positiveNegativeAspect";
    positionStart = 10;
    positionEnd = 11;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "a", "Positive",
      "b", "Negative",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
  }
}