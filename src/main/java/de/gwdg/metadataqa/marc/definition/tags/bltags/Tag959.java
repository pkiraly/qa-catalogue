package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Document Supply Status Flag
 */
public class Tag959 extends DataFieldDefinition {

  private static Tag959 uniqueInstance;

  private Tag959() {
    initialize();
    postCreation();
  }

  public static Tag959 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag959();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "959";
    label = "Document Supply Status Flag";
    mqTag = "DocumentSupplyStatus";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "f", "Document Supply status", "NR"
    );

    getSubfield("f")
      .setMqTag("status");
  }
}
