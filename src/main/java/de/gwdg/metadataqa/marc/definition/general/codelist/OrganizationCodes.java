package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.utils.EncodedValueFileReader;

/**
 * MARC Organization Codes
 * http://www.loc.gov/marc/organizations/orgshome.html
 * <p>
 * Note: this is not a full list!
 */
public class OrganizationCodes extends CodeList {

  private void initialize() {
    name = "MARC Organization Codes";
    url = "http://www.loc.gov/marc/organizations/orgshome.html";
    codes = EncodedValueFileReader.fileToCodeList("marc/organization-codes.csv");
    indexCodes();
  }

  private static OrganizationCodes uniqueInstance;

  private OrganizationCodes() {
    initialize();
  }

  public static OrganizationCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new OrganizationCodes();
    return uniqueInstance;
  }
}
