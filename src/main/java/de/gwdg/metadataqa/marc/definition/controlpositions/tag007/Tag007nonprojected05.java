package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

/**
 * Secondary support material
 * https://www.loc.gov/marc/bibliographic/bd007k.html
 */
public class Tag007nonprojected05 extends ControlfieldPositionDefinition {
  private static Tag007nonprojected05 uniqueInstance;

  private Tag007nonprojected05() {
    initialize();
  }

  public static Tag007nonprojected05 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007nonprojected05();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Secondary support material";
    id = "007nonprojected05";
    mqTag = "secondarySupportMaterial";
    positionStart = 5;
    positionEnd = 6;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007k.html";
    codes = Utils.generateCodes(
      " ", "No secondary support",
      "a", "Canvas",
      "b", "Bristol board",
      "c", "Cardboard/illustration board",
      "d", "Glass",
      "e", "Synthetic",
      "f", "Skin",
      "g", "Textile",
      "h", "Metal",
      "i", "Plastic",
      "l", "Vinyl",
      "m", "Mixed collection",
      "n", "Vellum",
      "o", "Paper",
      "p", "Plaster",
      "q", "Hardboard",
      "r", "Porcelain",
      "s", "Stone",
      "t", "Wood",
      "u", "Unknown",
      "v", "Leather",
      "w", "Parchment",
      "z", "Other",
      "|", "No attempt to code"
    );
    defaultCode = "|";
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
  }
}