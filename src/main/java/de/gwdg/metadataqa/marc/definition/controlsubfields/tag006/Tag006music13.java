package de.gwdg.metadataqa.marc.definition.controlsubfields.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Literary text for sound recordings
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006music13 extends ControlSubfieldDefinition {
  private static Tag006music13 uniqueInstance;

  private Tag006music13() {
    initialize();
    extractValidCodes();
  }

  public static Tag006music13 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006music13();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Literary text for sound recordings";
    id = "tag006music13";
    mqTag = "literaryTextForSoundRecordings";
    positionStart = 13;
    positionEnd = 15;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}