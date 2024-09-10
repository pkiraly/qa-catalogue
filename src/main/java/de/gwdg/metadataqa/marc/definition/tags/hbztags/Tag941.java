package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für BVB
 */
public class Tag941 extends DataFieldDefinition {

  private static Tag941 uniqueInstance;

  private Tag941() {
    initialize();
    postCreation();
  }

  public static Tag941 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag941();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "941";
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
