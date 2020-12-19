package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Cambridge University Library Location
 */
public class Tag963 extends DataFieldDefinition {

  private static Tag963 uniqueInstance;

  private Tag963() {
    initialize();
    postCreation();
  }

  public static Tag963 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag963();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "963";
    label = "Cambridge University Library Location";
    mqTag = "CambridgeUniversityLibraryLocation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Holdings statement", "NR",
      "b", "Other holdings", "NR",
      "c", "Shelfmark", "NR"
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

    getSubfield("b")
      .setCodes(
        "Y", "Other holdings",
        "N", "No other holdings"
      )
      .setMqTag("otherHoldings");

    getSubfield("c")
      .setMqTag("shelfmark");
  }
}
