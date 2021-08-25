package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Subject Access Fields – UVA
 */
public class Tag693 extends DataFieldDefinition {
  private static Tag693 uniqueInstance;

  private Tag693() {
    initialize();
    postCreation();
  }

  public static Tag693 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag693();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "693";
    label = "Local Subject Access Fields – UVA";
    mqTag = "LocalSubjectAccessFieldsUVA";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Dutch Basic Classification Codes – uncontrolled (thesis)", "NR",
      "b", "GTT – uncontrolled (thesis)", "NR"
    );

    getSubfield("a").setMqTag("DutchBasicClassificationCodesUncontrolled");
    getSubfield("b").setMqTag("GTTUncontrolled");
  }
}
