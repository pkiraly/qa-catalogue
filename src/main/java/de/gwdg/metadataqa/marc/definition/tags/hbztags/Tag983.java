package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Lokale Notation
 */
public class Tag983 extends DataFieldDefinition {

  private static Tag983 uniqueInstance;

  private Tag983() {
    initialize();
    postCreation();
  }

  public static Tag983 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag983();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "983";
    label = "Lokale Notation";
    mqTag = "lokaleNotation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Notation", "R",
      "b", "Notation", "R",
      "c", "Notation", "R",
      "d", "Beschreibung", "R",
      "z", "Herkunft", "NR"      
    );

  }
}
