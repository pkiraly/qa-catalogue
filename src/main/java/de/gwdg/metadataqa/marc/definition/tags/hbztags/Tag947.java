package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für EKZ
 */
public class Tag947 extends DataFieldDefinition {

  private static Tag947 uniqueInstance;

  private Tag947() {
    initialize();
    postCreation();
  }

  public static Tag947 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag947();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "947";
    label = "reserviert für EKZ";
    mqTag = "ReserviertFürEKZ";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "EKZ-Feld", "R"
    );


  }
}
