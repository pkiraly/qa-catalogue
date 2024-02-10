package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

/**
 * Platform construction type
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing06 extends ControlfieldPositionDefinition {
  private static Tag007remoteSensing06 uniqueInstance;

  private Tag007remoteSensing06() {
    initialize();
  }

  public static Tag007remoteSensing06 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing06();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Platform construction type";
    id = "007remoteSensing06";
    mqTag = "platformConstructionType";
    positionStart = 6;
    positionEnd = 7;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "a", "Balloon",
      "b", "Aircraft--low altitude",
      "c", "Aircraft--medium altitude",
      "d", "Aircraft--high altitude",
      "e", "Manned spacecraft",
      "f", "Unmanned spacecraft",
      "g", "Land-based remote-sensing device",
      "h", "Water surface-based remote-sensing device",
      "i", "Submersible remote-sensing device",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(UseInterpret);
  }
}