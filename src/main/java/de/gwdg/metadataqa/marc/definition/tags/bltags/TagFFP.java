package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.DateValidator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Flag For Publication
 */
public class TagFFP extends DataFieldDefinition {

  private static TagFFP uniqueInstance;

  private TagFFP() {
    initialize();
    postCreation();
  }

  public static TagFFP getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagFFP();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "FFP";
    label = "Flag For Publication";
    mqTag = "flagForPublication";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Publication flag", "NR",
      "b", "Reason for publication", "NR"
    );

    getSubfield("a")
      .setCodes(
        "Y", "Y"
      )
      .setMqTag("publicationFlag");

    getSubfield("b")
      .setValidator(new RegexValidator("^[A-Z]+$"))
      .setMqTag("reasonForPublication");
  }
}
