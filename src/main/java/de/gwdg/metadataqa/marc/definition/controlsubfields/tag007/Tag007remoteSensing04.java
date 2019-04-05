package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Attitude of sensor
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing04 extends ControlSubfieldDefinition {
  private static Tag007remoteSensing04 uniqueInstance;

  private Tag007remoteSensing04() {
    initialize();
    extractValidCodes();
  }

  public static Tag007remoteSensing04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Attitude of sensor";
    id = "tag007remoteSensing04";
    mqTag = "attitudeOfSensor";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "a", "Low oblique",
      "b", "High oblique",
      "c", "Vertical",
      "n", "Not applicable",
      "u", "Unknown",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(UsageInterpret);
  }
}