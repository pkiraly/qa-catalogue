package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Produktsigel
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446
 */
public class Tag912 extends DataFieldDefinition {

  private static Tag912 uniqueInstance;

  private Tag912() {
    initialize();
    postCreation();
  }

  public static Tag912 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag912();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "912";
    label = "Produktsigel";
    mqTag = "produktsigel";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Kennzeichnungen für Nationallizenzen und digitale Sammlungen", "NR",
      "b", "Lizenzjahr", "NR"
    )
  }
}
