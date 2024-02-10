package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

/**
 * Sensor type
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing08 extends ControlfieldPositionDefinition {
  private static Tag007remoteSensing08 uniqueInstance;

  private Tag007remoteSensing08() {
    initialize();
  }

  public static Tag007remoteSensing08 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing08();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Sensor type";
    id = "007remoteSensing08";
    mqTag = "sensorType";
    positionStart = 8;
    positionEnd = 9;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "a", "Active",
      "b", "Passive",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(UseInterpret);
  }
}