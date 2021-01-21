package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Motion picture presentation format
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture04 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture04 uniqueInstance;

  private Tag007motionPicture04() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Motion picture presentation format";
    id = "tag007motionPicture04";
    mqTag = "motionPicturePresentationFormat";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "a", "Standard sound aperture (reduced frame)",
      "b", "Nonanamorphic (wide-screen)",
      "c", "3D",
      "d", "Anamorphic (wide-screen)",
      "e", "Other wide-screen format",
      "f", "Standard silent aperture (full frame)",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
    historicalCodes = Utils.generateCodes(
      "n", "Not applicable [OBSOLETE, 1983]"
    );
  }
}