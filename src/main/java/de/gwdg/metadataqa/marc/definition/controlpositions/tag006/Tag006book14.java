package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Index
 * same as 008/31
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book14 extends ControlfieldPositionDefinition {
  private static Tag006book14 uniqueInstance;

  private Tag006book14() {
    initialize();
  }


  public static Tag006book14 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006book14();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Index";
    id = "006book14";
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