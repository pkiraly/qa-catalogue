package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Added Entry Special Collections (Uncontrolled)
 */
public class Tag973 extends DataFieldDefinition {
  private static Tag973 uniqueInstance;

  private Tag973() {
    initialize();
    postCreation();
  }

  public static Tag973 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag973();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "973";
    label = "Local Added Entry Special Collections (Uncontrolled)";
    mqTag = "LocalAddedEntrySpecialCollectionsUncontrolled";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name", "NR",
      "b", "Place", "NR",
      "e", "Relator term", "NR"
    );

    getSubfield("a").setMqTag("Name");
    getSubfield("b").setMqTag("Place");
    getSubfield("e").setMqTag("RelatorTerm");
  }
}
