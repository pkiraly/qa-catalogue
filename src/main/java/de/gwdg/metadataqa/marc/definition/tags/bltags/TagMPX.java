package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Map Leader Data Element
 */
public class TagMPX extends DataFieldDefinition {

  private static TagMPX uniqueInstance;

  private TagMPX() {
    initialize();
    postCreation();
  }

  public static TagMPX getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagMPX();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "MPX";
    label = "Map Leader Data Element";
    mqTag = "MapLeaderDataElement";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Data element", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^\\w$"))
      .setMqTag("dataElement");
  }
}
