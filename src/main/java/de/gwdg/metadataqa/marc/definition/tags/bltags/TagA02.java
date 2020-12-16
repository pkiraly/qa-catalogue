package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Serial Acquisitions System Number
 */
public class TagA02 extends DataFieldDefinition {

  private static TagA02 uniqueInstance;

  private TagA02() {
    initialize();
    postCreation();
  }

  public static TagA02 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagA02();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "A02";
    label = "Serial Acquisitions System Number";
    mqTag = "serialAcquisitionsSystemNumber";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Serial id", "NR",
      "z", "Old id", "NR"
    );

    getSubfield("a")
      .setMqTag("serialId");

    getSubfield("z")
      .setMqTag("oldId");
  }
}
