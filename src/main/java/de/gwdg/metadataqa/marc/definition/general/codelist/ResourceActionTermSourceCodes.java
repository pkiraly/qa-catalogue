package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Resource Action Term Source Codes
 * https://www.loc.gov/standards/sourcelist/resource-action.html
 */
public class ResourceActionTermSourceCodes extends CodeList {

  private void initialize() {
    name = "Resource Action Term Source Codes";
    url = "https://www.loc.gov/standards/sourcelist/resource-action.html";
    codes = Utils.generateCodes(
      "pda", "Preservation & digitization actions: terminology for MARC 21 Field 583 (Mountain View, CA: RLG/ARL/LC Task Force on 583 Field)",
      "pet", "Preservation event types (Washington, DC: Library of Congress)",
      "stmanf", "Standard terminology for the MARC 21 Actions Note Field (Washington, DC: Library of Congress, Network Development and MARC Standards Office)"
    );
    indexCodes();
  }

  private static ResourceActionTermSourceCodes uniqueInstance;

  private ResourceActionTermSourceCodes() {
    initialize();
  }

  public static ResourceActionTermSourceCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new ResourceActionTermSourceCodes();
    return uniqueInstance;
  }
}
