package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Nature of entire work
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing24 extends ControlfieldPositionDefinition {
  private static Tag008continuing24 uniqueInstance;

  private Tag008continuing24() {
    initialize();
  }

  public static Tag008continuing24 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008continuing24();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Nature of entire work";
    id = "008continuing24";
    mqTag = "natureOfEntireWork";
    positionStart = 24;
    positionEnd = 25;
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
  }
}