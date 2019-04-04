package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;

/**
 * Physical medium
 * https://www.loc.gov/marc/bibliographic/bd007a.html
 */
public class Tag007map04 extends ControlSubfieldDefinition {
  private static Tag007map04 uniqueInstance;

  private Tag007map04() {
    initialize();
    extractValidCodes();
  }

  public static Tag007map04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007map04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Physical medium";
    id = "tag007map04";
    mqTag = "physicalMedium";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007a.html";
    codes = Utils.generateCodes(
      "a", "Paper",
      "b", "Wood",
      "c", "Stone",
      "d", "Metal",
      "e", "Synthetic",
      "f", "Skin",
      "g", "Textiles",
      "i", "Plastic",
      "j", "Glass",
      "l", "Vinyl",
      "n", "Vellum",
      "p", "Plaster",
      "q", "Flexible base photographic, positive",
      "r", "Flexible base photographic, negative",
      "s", "Non-flexible base photographic, positive",
      "t", "Non-flexible base photographic, negative",
      "u", "Unknown",
      "v", "Leather",
      "w", "Parchment",
      "y", "Other photographic medium",
      "z", "Other",
      "|", "No attempt to code"
    );
  }
}