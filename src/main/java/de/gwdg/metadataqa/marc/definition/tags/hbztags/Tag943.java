package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für BVB
 */
public class Tag943 extends DataFieldDefinition {

  private static Tag943 uniqueInstance;

  private Tag943() {
    initialize();
    postCreation();
  }

  public static Tag943 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag943();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "943";
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
