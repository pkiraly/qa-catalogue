package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.DateValidator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Block Export
 */
public class TagEXP extends DataFieldDefinition {

  private static TagEXP uniqueInstance;

  private TagEXP() {
    initialize();
    postCreation();
  }

  public static TagEXP getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagEXP();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "EXP";
    label = "Block Export";
    mqTag = "blockExport";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Block export", "NR",
      "d", "Embargo date", "NR"
    );

    getSubfield("a")
      .setCodes(
        "X", "X"
      )
      .setMqTag("blockExport");

    getSubfield("d")
      .setValidator(new DateValidator("yyyyMMdd"))
      .setMqTag("embargoDate");
  }
}
