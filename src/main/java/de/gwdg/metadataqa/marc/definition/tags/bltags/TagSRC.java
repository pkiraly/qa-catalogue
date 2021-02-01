package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Source
 */
public class TagSRC extends DataFieldDefinition {

  private static TagSRC uniqueInstance;

  private TagSRC() {
    initialize();
    postCreation();
  }

  public static TagSRC getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagSRC();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "SRC";
    label = "Source";
    mqTag = "Source";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Source", "NR",
      "b", "Physical file name", "NR"
    );

    getSubfield("a").setMqTag("source");
    getSubfield("b").setMqTag("fileName");
  }
}
