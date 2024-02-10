package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Nature of entire work
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006continuing07 extends ControlfieldPositionDefinition {
  private static Tag006continuing07 uniqueInstance;

  private Tag006continuing07() {
    initialize();
  }

  public static Tag006continuing07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006continuing07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Nature of entire work";
    id = "006continuing07";
    mqTag = "natureOfEntireWork";
    positionStart = 7;
    positionEnd = 8;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}