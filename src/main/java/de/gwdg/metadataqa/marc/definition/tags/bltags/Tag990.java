package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Product Information Code
 */
public class Tag990 extends DataFieldDefinition {

  private static Tag990 uniqueInstance;

  private Tag990() {
    initialize();
    postCreation();
  }

  public static Tag990 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag990();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "990";
    label = "Product Information Code";
    mqTag = "ProductInformationCode";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Product information code", "R"
    );

    getSubfield("a")
      .setCodes(
        "I", "ISSN",
        "R", "Reports",
        "T", "Theses",
        "X", "Translations"
      )
      .setMqTag("product");
  }
}
