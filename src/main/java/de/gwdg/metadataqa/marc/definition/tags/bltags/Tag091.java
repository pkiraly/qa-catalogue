package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Previous Control Number (Document Supply Conference)
 */
public class Tag091 extends DataFieldDefinition {

  private static Tag091 uniqueInstance;

  private Tag091() {
    initialize();
    postCreation();
  }

  public static Tag091 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag091();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "091";
    label = "Previous Control Number (Document Supply Conference)";
    mqTag = "PreviousControlNumber";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Control number", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^c\\d{2}\\d{2}\\d+a?$"))
      .setMqTag("controlNumber");
  }
}
