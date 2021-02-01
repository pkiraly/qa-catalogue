package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Card Production Indicator
 */
public class Tag980 extends DataFieldDefinition {

  private static Tag980 uniqueInstance;

  private Tag980() {
    initialize();
    postCreation();
  }

  public static Tag980 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag980();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "980";
    label = "Card Production Indicator";
    mqTag = "CardProductionIndicator";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Card production indicator", "NR"
    );

    getSubfield("a")
      .setCodes("Card", "Card")
      .setMqTag("cardProduction");
  }
}
