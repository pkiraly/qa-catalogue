package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Production Category
 */
public class Tag917 extends DataFieldDefinition {

  private static Tag917 uniqueInstance;

  private Tag917() {
    initialize();
    postCreation();
  }

  public static Tag917 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag917();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "917";
    label = "Production Category";
    mqTag = "ProductionCategory";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Production Category", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("category");
  }
}
