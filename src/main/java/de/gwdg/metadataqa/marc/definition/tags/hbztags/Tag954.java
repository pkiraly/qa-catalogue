package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für GBV
 */
public class Tag954 extends DataFieldDefinition {

  private static Tag954 uniqueInstance;

  private Tag954() {
    initialize();
    postCreation();
  }

  public static Tag954 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag954();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "954";
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
