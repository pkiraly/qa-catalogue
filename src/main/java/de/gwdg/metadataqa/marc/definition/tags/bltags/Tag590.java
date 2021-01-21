package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Document Supply General Note
 */
public class Tag590 extends DataFieldDefinition {

  private static Tag590 uniqueInstance;

  private Tag590() {
    initialize();
    postCreation();
  }

  public static Tag590 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag590();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "590";
    label = "Document Supply General Note";
    mqTag = "DocumentSupplyGeneralNote";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "General note", "NR"
    );

    getSubfield("a")
      .setMqTag("generalNote");
  }
}
