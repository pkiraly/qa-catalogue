package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Science Museum Library Location
 */
public class Tag964 extends DataFieldDefinition {

  private static Tag964 uniqueInstance;

  private Tag964() {
    initialize();
    postCreation();
  }

  public static Tag964 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag964();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "964";
    label = "Science Museum Library Location";
    mqTag = "ScienceMuseumLibraryLocation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Holdings statement", "NR",
      "c", "Shelfmark", "NR",
      "d", "Shelfmark qualifier", "NR",
      "e", "Earlier shelfmark", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*\\*A=[1-4\\?]$"))
      .setMqTag("holdings");
    /*
     TODO
          .setCodes(
        "*A=1", "over 95% of a title is in stock.",
        "*A=2", "75%-95% of a title is in stock.",
        "*A=3", "50-75% of a title is in stock.",
        "*A=4", "up to 50% of a title is in stock.",
        "*A=?", "the completeness is not known."
      )
     */

    // contains the UDC number at which an item is shelved
    getSubfield("c")
      .setMqTag("shelfmark");

    // publication size
    getSubfield("d")
      .setCodes(
        "0", "0",
        "A", "A",
        "F", "F",
        "L", "L",
        "O", "O",
        "Q", "Q"
      )
      .setMqTag("qualifier");

    getSubfield("c")
      .setMqTag("earlierShelfmark");
  }
}
