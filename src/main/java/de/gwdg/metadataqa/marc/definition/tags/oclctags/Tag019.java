package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * OCLC Control Number Cross-Reference
 * http://www.oclc.org/bibformats/en/0xx/019.html
 */
public class Tag019 extends DataFieldDefinition {

  private static Tag019 uniqueInstance;

  private Tag019() {
    initialize();
    postCreation();
  }

  public static Tag019 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag019();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "019";
    label = "OCLC Control Number Cross-Reference";
    mqTag = "OCLCControlNumber";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "http://www.oclc.org/bibformats/en/0xx/019.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "OCLC control number of merged and deleted record", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
