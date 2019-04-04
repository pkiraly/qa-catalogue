package de.gwdg.metadataqa.marc.definition.controlsubfields.leader;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Type of record
 * https://www.loc.gov/marc/bibliographic/bdleader.html
 */
public class Leader06 extends ControlSubfieldDefinition {
  private static Leader06 uniqueInstance;

  private Leader06() {
    initialize();
    extractValidCodes();
  }

  public static Leader06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Leader06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of record";
    id = "leader06";
    mqTag = "typeOfRecord";
    positionStart = 6;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bdleader.html";
    codes = Utils.generateCodes(
      "a", "Language material",
      "c", "Notated music",
      "d", "Manuscript notated music",
      "e", "Cartographic material",
      "f", "Manuscript cartographic material",
      "g", "Projected medium",
      "i", "Nonmusical sound recording",
      "j", "Musical sound recording",
      "k", "Two-dimensional nonprojectable graphic",
      "m", "Computer file",
      "o", "Kit",
      "p", "Mixed materials",
      "r", "Three-dimensional artifact or naturally occurring object",
      "t", "Manuscript language material"
    );

    historicalCodes = Utils.generateCodes(
      "b", "Archival and manuscripts control [OBSOLETE, 1995]",
      "h", "Microform publications [OBSOLETE, 1972] [USMARC only]",
      "n", "Special instructional material"
    );
  }
}