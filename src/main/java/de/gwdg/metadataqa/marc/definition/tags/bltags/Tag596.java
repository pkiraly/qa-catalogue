package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * National Bibliography Issue Number
 */
public class Tag596 extends DataFieldDefinition {

  private static Tag596 uniqueInstance;

  private Tag596() {
    initialize();
    postCreation();
  }

  public static Tag596 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag596();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "596";
    label = "Temporary Note";
    mqTag = "temporaryNote";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Temporary note", "NR"
    );

    getSubfield("a")
      .setMqTag("note");
  }
}
