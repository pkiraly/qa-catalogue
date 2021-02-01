package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Reduction ratio
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform06 extends ControlfieldPositionDefinition {
  private static Tag007microform06 uniqueInstance;

  private Tag007microform06() {
    initialize();
    extractValidCodes();
  }

  public static Tag007microform06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Reduction ratio";
    id = "007microform06";
    mqTag = "reductionRatio";
    positionStart = 6;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);

    // TODO: pattern
  }
}