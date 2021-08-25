package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Added Entry Uncontrolled Name
 */
public class Tag720 extends DataFieldDefinition {
  private static Tag720 uniqueInstance;

  private Tag720() {
    initialize();
    postCreation();
  }

  public static Tag720 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag720();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "720";
    label = "Added Entry Uncontrolled Name";
    mqTag = "AddedEntryUncontrolledName";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Local name", "NR",
      "b", "Local name & corporate name", "NR"
    );

    getSubfield("a").setMqTag("LocalName");
    getSubfield("b").setMqTag("LocalNameCorporateName");
  }
}
