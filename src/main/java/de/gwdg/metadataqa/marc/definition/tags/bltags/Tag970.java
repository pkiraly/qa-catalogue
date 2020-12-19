package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Collection Code
 */
public class Tag970 extends DataFieldDefinition {

  private static Tag970 uniqueInstance;

  private Tag970() {
    initialize();
    postCreation();
  }

  public static Tag970 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag970();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "970";
    label = "Collection Code";
    mqTag = "CollectionCode";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Collection code", "NR"
    );

    getSubfield("a")
      .setMqTag("code");
  }
}
