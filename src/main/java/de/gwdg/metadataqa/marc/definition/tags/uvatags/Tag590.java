package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local General Note (Public)
 */
public class Tag590 extends DataFieldDefinition {
  private static Tag590 uniqueInstance;

  private Tag590() {
    initialize();
    postCreation();
  }

  public static Tag590 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag590();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "590";
    label = "Local General Note (Public)";
    mqTag = "LocalGeneralNotePublic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Local General Note", "NR"
    );

    getSubfield("a").setMqTag("LocalGeneralNote");
  }
}
