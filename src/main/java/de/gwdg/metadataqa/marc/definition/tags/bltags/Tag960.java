package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Normalised Place of Publication
 */
public class Tag960 extends DataFieldDefinition {

  private static Tag960 uniqueInstance;

  private Tag960() {
    initialize();
    postCreation();
  }

  public static Tag960 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag960();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "960";
    label = "Normalised Place of Publication";
    mqTag = "normalisedPlaceOfPublication";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator("Printing instruction")
      .setCodes(
        "0", "Printing data",
        "1", "Non-printing data"
      )
      .setMqTag("printingInstruction");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Normalised place of publication", "NR"
    );

    getSubfield("a")
      .setMqTag("place");
  }
}
