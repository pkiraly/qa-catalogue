package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Type of continuing resource
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing04 extends ControlfieldPositionDefinition {
  private static Tag006continuing04 uniqueInstance;

  private Tag006continuing04() {
    initialize();
  }


  public static Tag006continuing04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006continuing04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of continuing resource";
    id = "006continuing04";
    mqTag = "typeOfContinuingResource";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      " ", "None of the following",
      "d", "Updating database",
      "l", "Updating loose-leaf",
      "m", "Monographic series",
      "n", "Newspaper",
      "p", "Periodical",
      "w", "Updating Web site",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}