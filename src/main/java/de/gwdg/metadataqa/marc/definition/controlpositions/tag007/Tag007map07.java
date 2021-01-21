package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Positive/negative aspect
 * https://www.loc.gov/marc/bibliographic/bd007a.html
 */
public class Tag007map07 extends ControlfieldPositionDefinition {
  private static Tag007map07 uniqueInstance;

  private Tag007map07() {
    initialize();
    extractValidCodes();
  }

  public static Tag007map07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007map07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Positive/negative aspect";
    id = "tag007map07";
    mqTag = "positiveNegativeAspect";
    positionStart = 7;
    positionEnd = 8;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007a.html";
    codes = Utils.generateCodes(
      "a", "Positive",
      "b", "Negative",
      "m", "Mixed polarity",
      "n", "Not applicable",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    historicalCodes = Utils.generateCodes(
      "u", "Unknown [OBSOLETE, 1997] [CAN/MARC only]"
    );
  }
}