package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Reference to Items in Printed Catalogues
 */
public class Tag594 extends DataFieldDefinition {

  private static Tag594 uniqueInstance;

  private Tag594() {
    initialize();
    postCreation();
  }

  public static Tag594 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag594();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "594";
    label = "Reference to Items in Printed Catalogues";
    mqTag = "ReferenceToItemsInPrintedCatalogues";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Note of earliest British Library edition", "NR",
      "b", "Reference to items in printed catalogues", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*\\.$"))
      .setMqTag("note");

    getSubfield("b")
      .setValidator(new RegexValidator("^.*\\.$"))
      .setMqTag("reference");
  }
}
