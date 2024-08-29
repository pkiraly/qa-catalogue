package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Redaktionelle Bemerkungen
 */
public class Tag591 extends DataFieldDefinition {

  private static Tag591 uniqueInstance;

  private Tag591() {
    initialize();
    postCreation();
  }

  public static Tag591 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag591();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "591";
    label = "Redaktionelle Bemerkungen";
    mqTag = "RedaktionelleBemerkungen";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Redaktionelle Bemerkungen", "R"
    );

  }
}
