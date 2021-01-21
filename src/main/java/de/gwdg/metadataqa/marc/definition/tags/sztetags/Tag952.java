package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in SZTE
 */
public class Tag952 extends DataFieldDefinition {

  private static Tag952 uniqueInstance;

  private Tag952() {
    initialize();
    postCreation();
  }

  public static Tag952 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag952();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "952";
    label = "Video";
    mqTag = "Video";
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
