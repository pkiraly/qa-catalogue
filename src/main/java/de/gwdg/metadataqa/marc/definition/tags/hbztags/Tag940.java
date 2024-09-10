package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für BVB
 */
public class Tag940 extends DataFieldDefinition {

  private static Tag940 uniqueInstance;

  private Tag940() {
    initialize();
    postCreation();
  }

  public static Tag940 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag940();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "940";
    label = "reserviert für BVB";
    mqTag = "ReserviertFürBVB";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "BVB-Feld", "R"
    );


  }
}
