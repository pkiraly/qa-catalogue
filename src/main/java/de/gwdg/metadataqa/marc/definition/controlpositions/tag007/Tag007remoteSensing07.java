package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

/**
 * Platform use category
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing07 extends ControlfieldPositionDefinition {
  private static Tag007remoteSensing07 uniqueInstance;

  private Tag007remoteSensing07() {
    initialize();
  }

  public static Tag007remoteSensing07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Platform use category";
    id = "007remoteSensing07";
    mqTag = "platformUseCategory";
    positionStart = 7;
    positionEnd = 8;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "a", "Meteorological",
      "b", "Surface observing",
      "c", "Space observing",
      "m", "Mixed uses",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(UseInterpret);
  }
}