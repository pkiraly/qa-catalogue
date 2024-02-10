package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Index
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006map14 extends ControlfieldPositionDefinition {
  private static Tag006map14 uniqueInstance;

  private Tag006map14() {
    initialize();
  }

  public static Tag006map14 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006map14();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Index";
    id = "006map14";
    mqTag = "index";
    positionStart = 14;
    positionEnd = 15;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "0", "No index",
      "1", "Index present",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect);
  }
}