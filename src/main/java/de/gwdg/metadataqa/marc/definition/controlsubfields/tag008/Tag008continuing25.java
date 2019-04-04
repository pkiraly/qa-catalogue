package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Nature of contents
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing25 extends ControlSubfieldDefinition {
  private static Tag008continuing25 uniqueInstance;

  private Tag008continuing25() {
    initialize();
    extractValidCodes();
  }

  public static Tag008continuing25 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008continuing25();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Nature of contents";
    id = "tag008continuing25";
    mqTag = "natureOfContents";
    positionStart = 25;
    positionEnd = 28;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
    codes = Utils.generateCodes(
      " ", "Not specified",
      "a", "Abstracts/summaries",
      "b", "Bibliographies",
      "c", "Catalogs",
      "d", "Dictionaries",
      "e", "Encyclopedias",
      "f", "Handbooks",
      "g", "Legal articles",
      "h", "Biography",
      "i", "Indexes",
      "k", "Discographies",
      "l", "Legislation",
      "m", "Theses",
      "n", "Surveys of literature in a subject area",
      "o", "Reviews",
      "p", "Programmed texts",
      "q", "Filmographies",
      "r", "Directories",
      "s", "Statistics",
      "t", "Technical reports",
      "u", "Standards/specifications",
      "v", "Legal cases and case notes",
      "w", "Law reports and digests",
      "y", "Yearbooks",
      "z", "Treaties",
      "5", "Calendars",
      "6", "Comics/graphic novels",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "3", "Discographies [OBSOLETE, 1997]",
      "4", "Filmographies [OBSOLETE, 1997]"
    );

    repeatableContent = true;
    unitLength = 1;
  }
}