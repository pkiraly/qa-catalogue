package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Cataloguer’s Note
 */
public class Tag985 extends DataFieldDefinition {

  private static Tag985 uniqueInstance;

  private Tag985() {
    initialize();
    postCreation();
  }

  public static Tag985 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag985();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "985";
    label = "Cataloguer’s Note";
    mqTag = "CataloguersNote";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Cataloguer’s Note", "NR"
    );

    getSubfield("a")
      .setMqTag("note");
  }
}
