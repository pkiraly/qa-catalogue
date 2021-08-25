package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Subject Access Fields - HvA
 */
public class Tag690 extends DataFieldDefinition {
  private static Tag690 uniqueInstance;

  private Tag690() {
    initialize();
    postCreation();
  }

  public static Tag690 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag690();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "690";
    label = "Local Subject Access Fields - HvA";
    mqTag = "LocalSubjectAccessFieldsHvA";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Subject (incl. thesis)", "NR"
    );

    getSubfield("a").setMqTag("Subject");
  }
}
