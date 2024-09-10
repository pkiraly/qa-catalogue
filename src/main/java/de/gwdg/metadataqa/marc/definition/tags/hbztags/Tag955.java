package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für HeBIS
 */
public class Tag955 extends DataFieldDefinition {

  private static Tag955 uniqueInstance;

  private Tag955() {
    initialize();
    postCreation();
  }

  public static Tag955 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag955();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "955";
    label = "reserviert für HeBIS";
    mqTag = "ReserviertFürHeBIS";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "HeBIS-Feld", "R"
    );


  }
}
