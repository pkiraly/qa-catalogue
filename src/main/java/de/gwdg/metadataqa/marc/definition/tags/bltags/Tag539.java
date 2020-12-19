package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Location of Filmed Copy (ESTC)
 */
public class Tag539 extends DataFieldDefinition {

  private static Tag539 uniqueInstance;

  private Tag539() {
    initialize();
    postCreation();
  }

  public static Tag539 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag539();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "539";
    label = "Location of Filmed Copy (ESTC)";
    mqTag = "LocationOfFilmedCopyESTC";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Location", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*\\.$"))
      .setMqTag("location");
  }
}
