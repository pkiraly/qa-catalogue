package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Price Type Code Source Codes
 * http://www.loc.gov/standards/sourcelist/price-type.html
 */
public class AvailabilityStatusCodeSourceCodes extends CodeList {

  private void initialize() {
    name = "Price Type Code Source Codes";
    url = "http://www.loc.gov/standards/sourcelist/price-type.html";
    codes = Utils.generateCodes(
      "onixas", "ONIX Product Availability Codes List number 54"
    );
    indexCodes();
  }

  private static AvailabilityStatusCodeSourceCodes uniqueInstance;

  private AvailabilityStatusCodeSourceCodes() {
    initialize();
  }

  public static AvailabilityStatusCodeSourceCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new AvailabilityStatusCodeSourceCodes();
    return uniqueInstance;
  }
}
