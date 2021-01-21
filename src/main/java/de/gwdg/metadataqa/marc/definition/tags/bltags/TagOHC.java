package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Original Holding Count
 */
public class TagOHC extends DataFieldDefinition {

  private static TagOHC uniqueInstance;

  private TagOHC() {
    initialize();
    postCreation();
  }

  public static TagOHC getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagOHC();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "OHC";
    label = "Original Holding Count";
    mqTag = "OriginalHoldingCount";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Original holding count", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^\\d+$"))
      .setMqTag("originalHoldingCount");
  }
}
