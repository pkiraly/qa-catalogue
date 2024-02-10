package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Nature of contents
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag008book24 extends ControlfieldPositionDefinition {
  private static Tag008book24 uniqueInstance;

  private Tag008book24() {
    initialize();
  }

  public static Tag008book24 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008book24();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Nature of contents";
    id = "008book24";
    mqTag = "natureOfContents";
    positionStart = 24;
    positionEnd = 28;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008b.html";
    codes = Utils.generateCodes(
      " ", "No specified nature of contents",
      "a", "Abstracts/summaries",
      "b", "Bibliographies",
      "c", "Catalogs",
      "d", "Dictionaries",
      "e", "Encyclopedias",
      "f", "Handbooks",
      "g", "Legal articles",
      "i", "Indexes",
      "j", "Patent document",
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
      "2", "Offprints",
      "5", "Calendars",
      "6", "Comics/graphic novels",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "h", "Handbooks [OBSOLETE]",
      "x", "Technical reports [OBSOLETE, 1997]",
      "3", "Discographies [OBSOLETE, 1997]",
      "4", "Filmographies [OBSOLETE, 1997]"
    );

    repeatableContent = true;
    unitLength = 1;
  }
}