package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Shelving Location
 */
public class Tag955 extends DataFieldDefinition {

  private static Tag955 uniqueInstance;

  private Tag955() {
    initialize();
    postCreation();
  }

  public static Tag955 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag955();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "955";
    label = "Shelving Location";
    mqTag = "shelvingLocation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Shelfmark", "NR",
      "b", "Location", "NR"
    );

    getSubfield("a")
      .setMqTag("shelfmark");

    getSubfield("b")
      .setCodes(
        "MSS", "Manuscripts",
        "OH", "Orbit House",
        "SS", "Store Street"
      )
      .setMqTag("location");
  }
}
