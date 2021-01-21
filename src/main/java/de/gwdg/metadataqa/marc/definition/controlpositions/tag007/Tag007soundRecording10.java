package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Kind of material
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording10 extends ControlfieldPositionDefinition {
  private static Tag007soundRecording10 uniqueInstance;

  private Tag007soundRecording10() {
    initialize();
    extractValidCodes();
  }

  public static Tag007soundRecording10 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording10();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Kind of material";
    id = "tag007soundRecording10";
    mqTag = "kindOfMaterial";
    positionStart = 10;
    positionEnd = 11;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "a", "Lacquer coating",
      "b", "Cellulose nitrate",
      "c", "Acetate tape with ferrous oxide",
      "g", "Glass with lacquer",
      "i", "Aluminum with lacquer",
      "l", "Metal",
      "m", "Plastic with metal",
      "n", "Not applicable",
      "p", "Plastic",
      "r", "Paper with lacquer or ferrous oxide",
      "s", "Shellac",
      "w", "Wax",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
  }
}