package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für EKZ
 */
public class Tag949 extends DataFieldDefinition {

  private static Tag949 uniqueInstance;

  private Tag949() {
    initialize();
    postCreation();
  }

  public static Tag949 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag949();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "949";
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
