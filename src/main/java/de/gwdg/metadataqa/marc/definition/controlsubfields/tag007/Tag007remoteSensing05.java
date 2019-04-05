package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Cloud cover
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing05 extends ControlSubfieldDefinition {
  private static Tag007remoteSensing05 uniqueInstance;

  private Tag007remoteSensing05() {
    initialize();
    extractValidCodes();
  }

  public static Tag007remoteSensing05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Cloud cover";
    id = "tag007remoteSensing05";
    mqTag = "cloudCover";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "0", "0-9%",
      "1", "10-19%",
      "2", "20-29%",
      "3", "30-39%",
      "4", "40-49%",
      "5", "50-59%",
      "6", "60-69%",
      "7", "70-79%",
      "8", "80-89%",
      "9", "90-100%",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(UseInterpret);
  }
}