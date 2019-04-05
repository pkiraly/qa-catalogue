package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Reduction ratio
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform06 extends ControlSubfieldDefinition {
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
    id = "tag007microform06";
    mqTag = "reductionRatio";
    positionStart = 6;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);

    // TODO: pattern
  }
}