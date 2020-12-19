package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Transliteration Statement
 */
public class Tag954 extends DataFieldDefinition {

  private static Tag954 uniqueInstance;

  private Tag954() {
    initialize();
    postCreation();
  }

  public static Tag954 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag954();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "954";
    label = "Transliteration Statement";
    mqTag = "Transliteration";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Transliteration statement", "NR"
    );

    getSubfield("a").setMqTag("transliteration");
  }
}
