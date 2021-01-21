package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Document Supply Acquisitions Indicator
 */
public class Tag966 extends DataFieldDefinition {

  private static Tag966 uniqueInstance;

  private Tag966() {
    initialize();
    postCreation();
  }

  public static Tag966 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag966();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "966";
    label = "Document Supply Acquisitions Indicator";
    mqTag = "DocumentSupplyAcquisitionsIndicator";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "l", "Acquisitions record indicator", "NR",
      "u", "Acquisitions status indicator", "NR"
    );

    getSubfield("l")
      .setValidator(new RegexValidator("^(ACQUIS|SPACQ) .*[^\\.]$"))
      .setMqTag("recordIndicator");
    /* TODO
    .setCodes(
      "ACQUIS", "Acquisitions",
      "SPACQ", "Special Acquisitions"
    )
     */

    getSubfield("u")
      .setCodes(
        "o", "on order",
        "c", "cancelled"
      )
      .setMqTag("status");
  }
}
