package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Deskriptor
 */
public class Tag993 extends DataFieldDefinition {

  private static Tag993 uniqueInstance;

  private Tag993() {
    initialize();
    postCreation();
  }

  public static Tag993 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag993();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "993";
    label = "Deskriptor";
    mqTag = "Deskriptor";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator("undefined")
    .setCodes(  
      "0", "..."
    )
    .setMqTag("undefined");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Deskriptor", "R"
    );


  }
}
