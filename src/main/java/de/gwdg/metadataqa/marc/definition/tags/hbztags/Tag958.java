package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * reserviert für HeBIS
 */
public class Tag958 extends DataFieldDefinition {

  private static Tag958 uniqueInstance;

  private Tag958() {
    initialize();
    postCreation();
  }

  public static Tag958 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag958();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "958";
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
