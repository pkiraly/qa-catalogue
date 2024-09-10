package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für KOBV
 */
public class Tag968 extends DataFieldDefinition {

  private static Tag968 uniqueInstance;

  private Tag968() {
    initialize();
    postCreation();
  }

  public static Tag968 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag968();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "968";
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
