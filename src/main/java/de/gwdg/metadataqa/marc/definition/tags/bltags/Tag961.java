package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Sheet Index Note
 */
public class Tag961 extends DataFieldDefinition {

  private static Tag961 uniqueInstance;

  private Tag961() {
    initialize();
    postCreation();
  }

  public static Tag961 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag961();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "961";
    label = "Sheet Index Note";
    mqTag = "SheetIndexNote";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Sheet index note", "NR",
      "b", "Shelfmark", "NR"
    );

    getSubfield("a")
      .setMqTag("note");

    getSubfield("b")
      .setMqTag("shelfmark");
  }
}
