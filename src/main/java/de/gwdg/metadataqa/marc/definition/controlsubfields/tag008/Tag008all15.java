package de.gwdg.metadataqa.marc.definition.controlsubfields.tag008;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Place of publication, production, or execution
 * https://www.loc.gov/marc/bibliographic/bd008a.html
 */
public class Tag008all15 extends ControlSubfieldDefinition {
  private static Tag008all15 uniqueInstance;

  private Tag008all15() {
    initialize();
    extractValidCodes();
  }

  public static Tag008all15 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008all15();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Place of publication, production, or execution";
    id = "tag008all15";
    mqTag = "placeOfPublicationProductionOrExecution";
    positionStart = 15;
    positionEnd = 18;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008a.html";
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    setCodeList(CountryCodes.getInstance());

    // TODO: pattern?

    /*
    xx# - No place, unknown, or undetermined
    vp# - Various places
    [aaa] - Three-character alphabetic code
    [aa#] - Two-character alphabetic code
     */
  }
}