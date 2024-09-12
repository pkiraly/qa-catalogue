package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für NB Schweiz und Schweiz
 */
public class Tag979 extends DataFieldDefinition {

  private static Tag979 uniqueInstance;

  private Tag979() {
    initialize();
    postCreation();
  }

  public static Tag979 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag979();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "979";
    label = "reserviert für NB Schweiz und Schweiz";
    mqTag = "ReserviertFürNB Schweiz und Schweiz";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "NB Schweiz und Schweiz-Feld", "R"
    );


  }
}
