package de.gwdg.metadataqa.marc.definition.tags.control;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;

/**
 * Control Number Identifier
 * https://www.loc.gov/marc/bibliographic/bd005.html
 */
public class Control005Definition extends DataFieldDefinition {

  private static Control005Definition uniqueInstance;

  private Control005Definition() {
    initialize();
    postCreation();
  }

  public static Control005Definition getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Control005Definition();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "005";
    label = "Date and Time of Latest Transaction";
    mqTag = "LatestTransactionTime";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd005.html";

    /** TODO
     * parse datetime: yyyymmddhhmmss.f
     * 005/00-07:[ManagementProcess]
     * 005/08-15:[ManagementProcess]
     */
  }
}
