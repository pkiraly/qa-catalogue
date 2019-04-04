package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Base of film
 * https://www.loc.gov/marc/bibliographic/bd007h.html
 */
public class Tag007microform12 extends ControlSubfieldDefinition {
  private static Tag007microform12 uniqueInstance;

  private Tag007microform12() {
    initialize();
    extractValidCodes();
  }

  public static Tag007microform12 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007microform12();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Base of film";
    id = "tag007microform12";
    mqTag = "baseOfFilm";
    positionStart = 12;
    positionEnd = 13;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007h.html";
    codes = Utils.generateCodes(
      "a", "Safety base, undetermined",
      "c", "Safety base, acetate undetermined",
      "d", "Safety base, diacetate",
      "i", "Nitrate base",
      "m", "Mixed base (nitrate and safety)",
      "n", "Not applicable",
      "p", "Safety base, polyester",
      "r", "Safety base, mixed",
      "t", "Safety base, triacetate",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    historicalCodes = Utils.generateCodes(
      "b", "Not safety base [OBSOLETE, 1991]"
    );
  }
}