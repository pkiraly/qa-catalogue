package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Editing or Error Message
 */
public class Tag597 extends DataFieldDefinition {

  private static Tag597 uniqueInstance;

  private Tag597() {
    initialize();
    postCreation();
  }

  public static Tag597 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag597();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "597";
    label = "Editing or Error Message";
    mqTag = "EditingOrErrorMessage";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Unconverted legacy field or Message relating to conversion, deduplication and enhancement", "NR",
      "b", "Information relating to record enhancement", "NR"
    );

    getSubfield("a")
      .setMqTag("legacyFieldOrMessage");

    getSubfield("b")
      .setMqTag("recordEnhancement");
  }
}
