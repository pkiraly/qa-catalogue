package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Accompanying matter
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music24 extends ControlfieldPositionDefinition {
  private static Tag008music24 uniqueInstance;

  private Tag008music24() {
    initialize();
    extractValidCodes();
  }

  public static Tag008music24 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008music24();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Accompanying matter";
    id = "008music24";
    mqTag = "accompanyingMatter";
    positionStart = 24;
    positionEnd = 30;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
    codes = Utils.generateCodes(
      " ", "No accompanying matter",
      "a", "Discography",
      "b", "Bibliography",
      "c", "Thematic index",
      "d", "Libretto or text",
      "e", "Biography of composer or author",
      "f", "Biography of performer or history of ensemble",
      "g", "Technical and/or historical information on instruments",
      "h", "Technical information on music",
      "i", "Historical information",
      "k", "Ethnological information",
      "r", "Instructional materials",
      "s", "Music",
      "z", "Other",
      "|", "No attempt to code"
    );
    repeatableContent = true;
    unitLength = 1;

    historicalCodes = Utils.generateCodes(
      "g", "Punched paper tape [OBSOLETE, 1987]",
      "n", "Not applicable [OBSOLETE, 1980]",
      "j", "Historical information other than music [OBSOLETE, 1980]",
      "l", "Biography of arranger or transcriber [OBSOLETE, 1997]"
    );
  }
}