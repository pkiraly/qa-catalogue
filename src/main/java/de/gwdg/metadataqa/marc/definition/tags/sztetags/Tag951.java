package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in SZTE
 */
public class Tag951 extends DataFieldDefinition {

  private static Tag951 uniqueInstance;

  private Tag951() {
    initialize();
    postCreation();
  }

  public static Tag951 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag951();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "951";
    label = "Picture";
    mqTag = "Picture";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "f", "File name", "NR",
      "x", "ID", "NR",
      "z", "Access type", "NR"
    );
  }
}
