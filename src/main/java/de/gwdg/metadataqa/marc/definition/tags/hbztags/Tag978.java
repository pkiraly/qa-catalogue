package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für NB Schweiz und Schweiz
 */
public class Tag978 extends DataFieldDefinition {

  private static Tag978 uniqueInstance;

  private Tag978() {
    initialize();
    postCreation();
  }

  public static Tag978 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag978();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "978";
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
