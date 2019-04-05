package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Cataloging source
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all39 extends ControlSubfieldDefinition {
  private static Tag008all39 uniqueInstance;

  private Tag008all39() {
    initialize();
    extractValidCodes();
  }

  public static Tag008all39 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008all39();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Cataloging source";
    id = "tag008all39";
    mqTag = "catalogingSource";
    positionStart = 39;
    positionEnd = 40;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
    codes = Utils.generateCodes(
      " ", "National bibliographic agency",
      "c", "Cooperative cataloging program",
      "d", "Other",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(ManagementProcess);
    historicalCodes = Utils.generateCodes(
      "a", "National Agricultural Library [OBSOLETE, 1997] [USMARC only]",
      "b", "National Library of Medicine [OBSOLETE, 1997] [USMARC only]",
      "l", "Library of Congress cataloguing [OBSOLETE, 1997] [CAN/MARC only]",
      "o", "Other institution cataloguing [OBSOLETE, 1997] [CAN/MARC only]",
      "n", "Report to New serials titles [OBSOLETE, 1997] [USMARC only]",
      "r", "Reporting library [OBSOLETE, 1997] [CAN/MARC only]"
    );
  }
}