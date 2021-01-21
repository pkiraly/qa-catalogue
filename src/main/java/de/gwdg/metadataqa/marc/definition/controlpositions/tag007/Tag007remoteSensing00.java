package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Category of material
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing00 extends ControlfieldPositionDefinition {
  private static Tag007remoteSensing00 uniqueInstance;

  private Tag007remoteSensing00() {
    initialize();
    extractValidCodes();
  }

  public static Tag007remoteSensing00 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing00();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Category of material";
    id = "tag007music00";
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