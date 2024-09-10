package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für GBV
 */
public class Tag950 extends DataFieldDefinition {

  private static Tag950 uniqueInstance;

  private Tag950() {
    initialize();
    postCreation();
  }

  public static Tag950 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag950();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "950";
    label = "reserviert für GBV";
    mqTag = "ReserviertFürGBV";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "GBV-Feld", "R"
    );


  }
}
