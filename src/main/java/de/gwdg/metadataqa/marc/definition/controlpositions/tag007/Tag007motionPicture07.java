package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Dimensions
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture07 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture07 uniqueInstance;

  private Tag007motionPicture07() {
    initialize();

  }

  public static Tag007motionPicture07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Dimensions";
    id = "007motionPicture07";
    mqTag = "dimensions";
    positionStart = 7;
    positionEnd = 8;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
    codes = Utils.generateCodes(
      "a", "Standard 8 mm.",
      "b", "Super 8 mm./single 8 mm.",
      "c", "9.5 mm.",
      "d", "16 mm.",
      "e", "28 mm.",
      "f", "35 mm.",
      "g", "70 mm.",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
  }
}