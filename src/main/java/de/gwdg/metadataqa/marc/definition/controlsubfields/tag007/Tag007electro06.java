package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Image bit depth
 * https://www.loc.gov/marc/bibliographic/bd007c.html
 */
public class Tag007electro06 extends ControlSubfieldDefinition {
  private static Tag007electro06 uniqueInstance;

  private Tag007electro06() {
    initialize();
    extractValidCodes();
  }

  public static Tag007electro06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007electro06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Image bit depth";
    id = "tag007electro06";
    mqTag = "imageBitDepth";
    positionStart = 6;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007c.html";
    codes = Utils.generateCodes(
      "001-999", "Exact bit depth",
      "mmm", "Multiple",
      "nnn", "Not applicable",
      "---", "Unknown",
      "|||", "No attempt to code"
    );
    getCode("001-999").setRange(true);
    functions = Arrays.asList(DiscoverySelect);
  }
}