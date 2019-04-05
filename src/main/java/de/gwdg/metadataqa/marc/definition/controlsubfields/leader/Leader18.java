package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Descriptive cataloging form
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader18 extends ControlSubfieldDefinition {
  private static Leader18 uniqueInstance;

  private Leader18() {
    initialize();
    extractValidCodes();
  }

  public static Leader18 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader18();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Descriptive cataloging form";
    id = "leader18";
    mqTag = "descriptiveCatalogingForm";
    positionStart = 18;
    positionEnd = 19;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    codes = Utils.generateCodes(
      " ", "Non-ISBD",
      "a", "AACR 2",
      "c", "ISBD punctuation omitted",
      "i", "ISBD punctuation included",
      "n", "Non-ISBD punctuation omitted",
      "u", "Unknown"
    );
    functions = Arrays.asList(ManagementProcess);

    historicalCodes = Utils.generateCodes(
      "p", "Record is in partial ISBD form [OBSOLETE, 1987]",
      "r", "Record is in provisional form [OBSOLETE, 1981]"
    );
  }
}