package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Linking Entry Finding Aids (EAD)
 */
public class Tag903 extends DataFieldDefinition {
  private static Tag903 uniqueInstance;

  private Tag903() {
    initialize();
    postCreation();
  }

  public static Tag903 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag903();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "903";
    label = "Local Linking Entry Finding Aids (EAD)";
    mqTag = "LocalLinkingEntryFindingAidsEAD";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Linking Entry EAD", "NR"
    );

    getSubfield("a").setMqTag("LinkingEntryEAD");
  }
}
