package de.gwdg.metadataqa.marc.definition.tags.genttags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag591 extends DataFieldDefinition {

  private static Tag591 uniqueInstance;

  private Tag591() {
    initialize();
    postCreation();
  }

  public static Tag591 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag591();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "591";
    label = "Locally defined field in Gent";
    mqTag = "GentLocallyDefinedField";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Value", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
