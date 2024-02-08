package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Modified record
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all38 extends ControlfieldPositionDefinition {
  private static Tag008all38 uniqueInstance;

  private Tag008all38() {
    initialize();

  }

  public static Tag008all38 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008all38();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Modified record";
    id = "008all38";
    mqTag = "modifiedRecord";
    positionStart = 38;
    positionEnd = 39;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
    codes = Utils.generateCodes(
      " ", "Not modified",
      "d", "Dashed-on information omitted",
      "o", "Completely romanized/printed cards romanized",
      "r", "Completely romanized/printed cards in script",
      "s", "Shortened",
      "x", "Missing characters",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(ManagementProcess);
    historicalCodes = Utils.generateCodes(
      "u", "Unknown [OBSOLETE] [CAN/MARC only]"
    );
  }
}