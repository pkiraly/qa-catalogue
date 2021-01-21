package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally Assigned NLM-type Call Number
 * http://www.oclc.org/bibformats/en/0xx/096.html
 */
public class Tag096 extends DataFieldDefinition {

  private static Tag096 uniqueInstance;

  private Tag096() {
    initialize();
    postCreation();
  }

  public static Tag096 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag096();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "096";
    label = "Locally Assigned NLM-type Call Number";
    mqTag = "LocallyAssignedNLMTypeCallNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://www.oclc.org/bibformats/en/0xx/096.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Classification number", "NR",
      "b", "Item number", "NR",
      "e", "Feature heading", "NR",
      "f", "Filing suffix", "NR"
    );

    getSubfield("a").setMqTag("classificationNumber");
    getSubfield("b").setMqTag("itemNumber");
    getSubfield("e").setMqTag("featureHeading");
    getSubfield("f").setMqTag("filingSuffix");
  }
}
