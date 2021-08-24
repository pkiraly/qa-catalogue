package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

/**
 * Abbreviated Title
 * http://www.loc.gov/marc/bibliographic/bd210.html
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

    ind1 = null;
    ind2 = null;

    setSubfieldsWithCardinality(
      "a", "Subject (incl. thesis)", "NR"
    );

    getSubfield("a").setMqTag("Subject");
  }
}
