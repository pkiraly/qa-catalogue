package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * In Aleph benötigte Informationen (Wickelfeld)
 */
public class Tag964 extends DataFieldDefinition {

  private static Tag964 uniqueInstance;

  private Tag964() {
    initialize();
    postCreation();
  }

  public static Tag964 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag964();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "964";
    label = "In Aleph benötigte Informationen (Wickelfeld)";
    mqTag = "InAlephbenötigteInformationenWickelfeld";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=724304028";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "A", "A", "R",
      "F", "F", "R",
      "V", "V", "R"
    );

  }
}
