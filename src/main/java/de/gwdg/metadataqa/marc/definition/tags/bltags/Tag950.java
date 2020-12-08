package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Library of Congress Subject (Cross-Reference)
 */
public class Tag950 extends DataFieldDefinition {

  private static Tag950 uniqueInstance;

  private Tag950() {
    initialize();
    postCreation();
  }

  public static Tag950 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag950();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "950";
    label = "Library of Congress Subject (Cross-Reference)";
    mqTag = "locSubjectCrossReference";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Topical term or geographic name entry element", "R",
      "s", "See/See also", "R",
      "x", "Subject or form subdivision", "R",
      "y", "Chronological subdivision", "R",
      "z", "Geographic subdivision", "R"
    );

    getSubfield("a").setMqTag("topicalTerm");
    getSubfield("s").setMqTag("see");
    getSubfield("x").setMqTag("subject");
    getSubfield("y").setMqTag("chronological");
    getSubfield("z").setMqTag("geographic");
  }
}
