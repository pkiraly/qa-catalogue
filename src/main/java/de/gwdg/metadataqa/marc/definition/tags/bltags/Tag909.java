package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Awaiting OCLC Upgrade
 */
public class Tag909 extends DataFieldDefinition {

  private static Tag909 uniqueInstance;

  private Tag909() {
    initialize();
    postCreation();
  }

  public static Tag909 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag909();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "909";
    label = "Awaiting OCLC Upgrade";
    mqTag = "AwaitingOclcUpgrade";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "OCLC control number", "NR",
      "b", "Note", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("oclcControlNumber");

    getSubfield("b")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("note");

  }
}
