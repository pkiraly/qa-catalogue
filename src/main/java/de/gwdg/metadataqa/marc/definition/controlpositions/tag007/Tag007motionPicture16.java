package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Completeness
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture16 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture16 uniqueInstance;

  private Tag007motionPicture16() {
    initialize();
    extractValidCodes();
  }

  public static Tag007motionPicture16 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture16();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Completeness";
    id = "tag007motionPicture16";
    mqTag = "completeness";
    positionStart = 16;
    positionEnd = 17;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "c", "Complete",
      "i", "Incomplete",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoverySelect, UseManage);
  }
}