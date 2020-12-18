package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Serials claim letter title
 */
public class TagLET extends DataFieldDefinition {

  private static TagLET uniqueInstance;

  private TagLET() {
    initialize();
    postCreation();
  }

  public static TagLET getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagLET();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "LET";
    label = "Serials claim letter title";
    mqTag = "SerialsClaimLetterTitle";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator("Number of non-filing characters")
      .setCodes("0-9", "0-9")
      .setMqTag("nonFilingCharacters");
    ind2.getCode("0-9").setRange(true);

    setSubfieldsWithCardinality(
      "a", "Serial claim letter title", "NR"
    );

    getSubfield("a").setMqTag("title");
  }
}
