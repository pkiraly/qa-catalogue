package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

/**
 * Altitude of sensor
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing03 extends ControlfieldPositionDefinition {
  private static Tag007remoteSensing03 uniqueInstance;

  private Tag007remoteSensing03() {
    initialize();
    extractValidCodes();
  }

  public static Tag007remoteSensing03 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing03();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Altitude of sensor";
    id = "007remoteSensing03";
    mqTag = "altitudeOfSensor";
    positionStart = 3;
    positionEnd = 4;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "a", "Surface",
      "b", "Airborne",
      "c", "Spaceborne",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(UseInterpret);
  }
}