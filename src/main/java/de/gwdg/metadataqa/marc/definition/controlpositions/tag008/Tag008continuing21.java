package de.gwdg.metadataqa.marc.definition.controlpositions.tag008;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

/**
 * Type of continuing resource
 * https://www.loc.gov/marc/bibliographic/bd008s.html
 */
public class Tag008continuing21 extends ControlfieldPositionDefinition {
  private static Tag008continuing21 uniqueInstance;

  private Tag008continuing21() {
    initialize();
    extractValidCodes();
  }

  public static Tag008continuing21 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag008continuing21();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Type of continuing resource";
    id = "008continuing21";
    mqTag = "typeOfContinuingResource";
    positionStart = 21;
    positionEnd = 22;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd008s.html";
    codes = Utils.generateCodes(
      " ", "None of the following",
      "d", "Updating database",
      "l", "Updating loose-leaf",
      "m", "Monographic series",
      "n", "Newspaper",
      "p", "Periodical",
      "w", "Updating Web site",
      "|", "No attempt to code"
    );
  }
}