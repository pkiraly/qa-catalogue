package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Accompanying matter
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006music07 extends ControlfieldPositionDefinition {
  private static Tag006music07 uniqueInstance;

  private Tag006music07() {
    initialize();
  }


  public static Tag006music07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006music07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Accompanying matter";
    id = "006music07";
    mqTag = "accompanyingMatter";
    positionStart = 7;
    positionEnd = 13;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
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
    functions = Arrays.asList(DiscoverySelect);
  }
}