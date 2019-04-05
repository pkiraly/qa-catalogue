package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Emulsion on film
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform10 extends ControlSubfieldDefinition {
  private static Tag007microform10 uniqueInstance;

  private Tag007microform10() {
    initialize();
    extractValidCodes();
  }

  public static Tag007microform10 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform10();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Emulsion on film";
    id = "tag007microform10";
    mqTag = "emulsionOnFilm";
    positionStart = 10;
    positionEnd = 11;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    codes = Utils.generateCodes(
      "a", "Silver halide",
      "b", "Diazo",
      "c", "Vesicular",
      "m", "Mixed emulsion",
      "n", "Not applicable",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
  }
}