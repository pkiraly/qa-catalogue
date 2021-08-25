package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Database Selector
 */
public class Tag911 extends DataFieldDefinition {
  private static Tag911 uniqueInstance;

  private Tag911() {
    initialize();
    postCreation();
  }

  public static Tag911 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag911();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "911";
    label = "Database Selector";
    mqTag = "DatabaseSelector";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    // Note: New field. Subfields to be determined.
  }
}
