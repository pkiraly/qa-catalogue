package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für KOBV
 */
public class Tag967 extends DataFieldDefinition {

  private static Tag967 uniqueInstance;

  private Tag967() {
    initialize();
    postCreation();
  }

  public static Tag967 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag967();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "967";
    label = "reserviert für KOBV";
    mqTag = "ReserviertFürKOBV";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "KOBV-Feld", "R"
    );


  }
}
