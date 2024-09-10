package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für OBV
 */
public class Tag972 extends DataFieldDefinition {

  private static Tag972 uniqueInstance;

  private Tag972() {
    initialize();
    postCreation();
  }

  public static Tag972 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag972();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "972";
    label = "reserviert für OBV";
    mqTag = "ReserviertFürOBV";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "OBV-Feld", "R"
    );


  }
}
