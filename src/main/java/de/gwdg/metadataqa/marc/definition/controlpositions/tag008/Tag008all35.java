package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;


import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseInterpret;

/**
 * Language
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all35 extends ControlfieldPositionDefinition {
  private static Tag008all35 uniqueInstance;

  private Tag008all35() {
    initialize();
    extractValidCodes();
  }

  public static Tag008all35 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008all35();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Language";
    id = "008all35";
    mqTag = "language";
    positionStart = 35;
    positionEnd = 38;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseInterpret);
    setCodeList(LanguageCodes.getInstance());
    // TODO: pattern?
  }
}