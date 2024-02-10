package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing00 extends ControlfieldPositionDefinition {
  private static Tag007remoteSensing00 uniqueInstance;

  private Tag007remoteSensing00() {
    initialize();
  }

  public static Tag007remoteSensing00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "007remoteSensing00";
    mqTag = "categoryOfMaterial";
    positionStart = 0;
    positionEnd = 1;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "r", "Remote-sensing image"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
  }
}