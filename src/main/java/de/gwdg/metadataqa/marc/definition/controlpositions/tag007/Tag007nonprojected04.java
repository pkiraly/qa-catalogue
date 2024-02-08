package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

/**
 * Primary support material
 * https://www.loc.gov/marc/bibliographic/bd007k.html
 */
public class Tag007nonprojected04 extends ControlfieldPositionDefinition {
  private static Tag007nonprojected04 uniqueInstance;

  private Tag007nonprojected04() {
    initialize();

  }

  public static Tag007nonprojected04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007nonprojected04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Primary support material";
    id = "007nonprojected04";
    mqTag = "primarySupportMaterial";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007k.html";
    codes = Utils.generateCodes(
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
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
  }
}