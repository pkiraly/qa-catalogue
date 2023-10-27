package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Temporal Term Source Codes
 * https://www.loc.gov/standards/sourcelist/temporal.html
 */
public class TemporalTermSourceCodes extends CodeList {

  private void initialize() {
    name = "Temporal Term Source Codes";
    url = "https://www.loc.gov/standards/sourcelist/temporal.html";
    codes = Utils.generateCodes(
    );
    indexCodes();
  }

  private static TemporalTermSourceCodes uniqueInstance;

  private TemporalTermSourceCodes() {
    initialize();
  }

  public static TemporalTermSourceCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TemporalTermSourceCodes();
    return uniqueInstance;
  }

}
