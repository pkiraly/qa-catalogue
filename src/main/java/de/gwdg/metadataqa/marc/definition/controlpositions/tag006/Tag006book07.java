package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Nature of contents
 * same as 008/24-27
 * https://www.loc.gov/marc/bibliographic/bd006.html
 * https://www.loc.gov/marc/bibliographic/bd008b.html
 */
public class Tag006book07 extends ControlfieldPositionDefinition {
  private static Tag006book07 uniqueInstance;

  private Tag006book07() {
    initialize();
  }

  public static Tag006book07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006book07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Nature of contents";
    id = "006book07";
    mqTag = "natureOfContents";
    positionStart = 7;
    positionEnd = 11;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
    repeatableContent = true;
    unitLength = 1;
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}