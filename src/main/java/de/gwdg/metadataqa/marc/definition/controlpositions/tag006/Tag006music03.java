package de.gwdg.metadataqa.marc.definition.controlpositions.tag006;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Format of music
 * https://www.loc.gov/marc/bibliographic/bd006.html
 */
public class Tag006music03 extends ControlfieldPositionDefinition {
  private static Tag006music03 uniqueInstance;

  private Tag006music03() {
    initialize();
    extractValidCodes();
  }

  public static Tag006music03 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag006music03();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Format of music";
    id = "006music03";
    mqTag = "formatOfMusic";
    positionStart = 3;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd006.html";
    codes = Utils.generateCodes(
      "a", "Full score",
      "b", "Miniature or study score",
      "c", "Accompaniment reduced for keyboard",
      "d", "Voice score with accompaniment omitted",
      "e", "Condensed score or piano-conductor score",
      "g", "Close score",
      "h", "Chorus score",
      "i", "Condensed score",
      "j", "Performer-conductor part",
      "k", "Vocal score",
      "l", "Score",
      "m", "Multiple score formats",
      "n", "Not applicable",
      "p", "Piano score",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect);
  }
}