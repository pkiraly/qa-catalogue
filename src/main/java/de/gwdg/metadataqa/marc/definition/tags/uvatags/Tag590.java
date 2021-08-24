package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

/**
 * Abbreviated Title
 * http://www.loc.gov/marc/bibliographic/bd210.html
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
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = null;
    ind2 = null;

    setSubfieldsWithCardinality(
      "a", "Local General Note", "NR"
    );

    getSubfield("a").setMqTag("LocalGeneralNote");
  }
}
