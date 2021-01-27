package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Date 1
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all07 extends ControlfieldPositionDefinition {
  private static Tag008all07 uniqueInstance;

  private Tag008all07() {
    initialize();
    extractValidCodes();
  }

  public static Tag008all07 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008all07();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Date 1";
    id = "008all07";
    mqTag = "date1";
    positionStart = 7;
    positionEnd = 11;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);

    // TODO: pattern?

  }
}