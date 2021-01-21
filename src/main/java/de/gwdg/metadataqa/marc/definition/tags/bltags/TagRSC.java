package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Remote Supply Collection
 */
public class TagRSC extends DataFieldDefinition {

  private static TagRSC uniqueInstance;

  private TagRSC() {
    initialize();
    postCreation();
  }

  public static TagRSC getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagRSC();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "RSC";
    label = "Remote Supply Collection";
    mqTag = "RemoteSupplyCollection";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Code", "NR"
    );

    getSubfield("a")
      .setCodes("Y", "Y")
      .setMqTag("code");
  }
}
