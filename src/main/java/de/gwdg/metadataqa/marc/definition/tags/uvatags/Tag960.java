package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Subject Access Field - Collection Code
 */
public class Tag960 extends DataFieldDefinition {
  private static Tag960 uniqueInstance;

  private Tag960() {
    initialize();
    postCreation();
  }

  public static Tag960 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag960();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "960";
    label = "Local Subject Access Field - Collection Code";
    mqTag = "LocalSubjectAccessFieldCollectionCode";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Collection Code", "NR"
    );

    getSubfield("a").setMqTag("CollectionCode");
  }
}
