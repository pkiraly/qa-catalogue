package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für KOBV
 */
public class Tag966 extends DataFieldDefinition {

  private static Tag966 uniqueInstance;

  private Tag966() {
    initialize();
    postCreation();
  }

  public static Tag966 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag966();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "966";
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
