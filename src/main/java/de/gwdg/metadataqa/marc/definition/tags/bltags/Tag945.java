package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * BL Local Title
 */
public class Tag945 extends DataFieldDefinition {

  private static Tag945 uniqueInstance;

  private Tag945() {
    initialize();
    postCreation();
  }

  public static Tag945 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag945();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "945";
    label = "BL Local Title";
    mqTag = "blLocalTitle";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator("Type of title")
      .setCodes(
        " ", "Document Supply Shelving Title",
        "1", "DLS Ingest Title"
      )
      .setMqTag("typeOfTitle");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "BL local title", "NR"
    );

    getSubfield("a")
      .setMqTag("title");
  }
}
