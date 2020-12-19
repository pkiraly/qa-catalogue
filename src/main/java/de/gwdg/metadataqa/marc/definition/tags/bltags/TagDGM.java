package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Digitised Record Match
 */
public class TagDGM extends DataFieldDefinition {

  private static TagDGM uniqueInstance;

  private TagDGM() {
    initialize();
    postCreation();
  }

  public static TagDGM getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagDGM();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "DGM";
    label = "Digitised Record Match";
    mqTag = "DigitisedRecordMatch";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "System number", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("systemNumber");
  }
}
