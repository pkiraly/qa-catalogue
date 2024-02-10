package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

/**
 * Base of film
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture12 extends ControlfieldPositionDefinition {
  private static Tag007motionPicture12 uniqueInstance;

  private Tag007motionPicture12() {
    initialize();
  }

  public static Tag007motionPicture12 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007motionPicture12();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Base of film";
    id = "007motionPicture12";
    mqTag = "baseOfFilm";
    positionStart = 12;
    positionEnd = 13;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
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
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
  }
}