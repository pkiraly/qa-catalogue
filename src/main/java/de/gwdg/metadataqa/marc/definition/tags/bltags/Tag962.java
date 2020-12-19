package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Colindale Location Flag
 */
public class Tag962 extends DataFieldDefinition {

  private static Tag962 uniqueInstance;

  private Tag962() {
    initialize();
    postCreation();
  }

  public static Tag962 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag962();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "962";
    label = "Colindale Location Flag";
    mqTag = "ColindaleLocationFlag";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Holdings statement", "NR",
      "c", "Location code", "NR",
      "f", "Status", "NR"
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

    getSubfield("c")
      .setCodes(
        "COL", "COL"
      )
      .setMqTag("location");

    getSubfield("f")
      .setMqTag("status");
  }
}
