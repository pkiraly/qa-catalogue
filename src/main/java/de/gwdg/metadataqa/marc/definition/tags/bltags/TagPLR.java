package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * PRIMO Large Record
 */
public class TagPLR extends DataFieldDefinition {

  private static TagPLR uniqueInstance;

  private TagPLR() {
    initialize();
    postCreation();
  }

  public static TagPLR getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagPLR();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "PLR";
    label = "PRIMO Large Record";
    mqTag = "PRIMOLargeRecord";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Code", "NR",
      "b", "System number of associated records", "NR"
    );

    getSubfield("a")
      .setCodes("Y", "Y")
      .setMqTag("code");

    getSubfield("b")
      .setValidator(new RegexValidator("^\\d+(,\\d+)*$"))
      .setMqTag("systemNumber");
  }
}
