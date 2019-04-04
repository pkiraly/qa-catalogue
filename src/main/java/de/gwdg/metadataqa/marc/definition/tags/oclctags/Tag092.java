package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally Assigned Dewey Call Number
 * http://www.oclc.org/bibformats/en/0xx/092.html
 */
public class Tag092 extends DataFieldDefinition {

  private static Tag092 uniqueInstance;

  private Tag092() {
    initialize();
    postCreation();
  }

  public static Tag092 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag092();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "092";
    label = "Locally Assigned Dewey Call Number";
    mqTag = "LocallyAssignedDeweyCallNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://www.oclc.org/bibformats/en/0xx/092.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Classification number", "NR",
      "b", "Item number", "NR",
      "e", "Feature heading", "NR",
      "f", "Filing suffix", "NR",
      "2", "Edition number", "NR"
    );

    getSubfield("a").setMqTag("classificationNumber");
    getSubfield("b").setMqTag("itemNumber");
    getSubfield("e").setMqTag("featureHeading");
    getSubfield("f").setMqTag("filingSuffix");
    getSubfield("2").setMqTag("source");
  }
}
