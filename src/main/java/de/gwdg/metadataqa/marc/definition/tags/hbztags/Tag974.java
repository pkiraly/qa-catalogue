package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für OBV
 */
public class Tag974 extends DataFieldDefinition {

  private static Tag974 uniqueInstance;

  private Tag974() {
    initialize();
    postCreation();
  }

  public static Tag974 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag974();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "974";
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
