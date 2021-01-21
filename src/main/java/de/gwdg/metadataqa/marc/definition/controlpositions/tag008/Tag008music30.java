package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Literary text for sound recordings
 * https://www.loc.gov/marc/bibliographic/bd008m.html
 */
public class Tag008music30 extends ControlfieldPositionDefinition {
  private static Tag008music30 uniqueInstance;

  private Tag008music30() {
    initialize();
    extractValidCodes();
  }

  public static Tag008music30 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008music30();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Literary text for sound recordings";
    id = "tag008music30";
    mqTag = "literaryTextForSoundRecordings";
    positionStart = 30;
    positionEnd = 32;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008m.html";
    codes = Utils.generateCodes(
      " ", "Item is a music sound recording",
      "a", "Autobiography",
      "b", "Biography",
      "c", "Conference proceedings",
      "d", "Drama",
      "e", "Essays",
      "f", "Fiction",
      "g", "Reporting",
      "h", "History",
      "i", "Instruction",
      "j", "Language instruction",
      "k", "Comedy",
      "l", "Lectures, speeches",
      "m", "Memoirs",
      "n", "Not applicable",
      "o", "Folktales",
      "p", "Poetry",
      "r", "Rehearsals",
      "s", "Sounds",
      "t", "Interviews",
      "z", "Other",
      "|", "No attempt to code"
    );
    repeatableContent = true;
    unitLength = 1;
  }
}