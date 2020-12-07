package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * National Bibliography Issue Number
 */
public class Tag592 extends DataFieldDefinition {

  private static Tag592 uniqueInstance;

  private Tag592() {
    initialize();
    postCreation();
  }

  public static Tag592 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag592();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "592";
    label = "Collaboration Note";
    mqTag = "collaborationNote";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Collaboration note", "NR"
    );

    getSubfield("a")
      .setMqTag("collaborationNote");
  }
}
