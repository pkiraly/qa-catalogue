package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * OCLC-MARC Transaction Code
 * http://www.oclc.org/bibformats/en/9xx/994.html
 */
public class Tag994 extends DataFieldDefinition {

  private static Tag994 uniqueInstance;

  private Tag994() {
    initialize();
    postCreation();
  }

  public static Tag994 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag994();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "994";
    label = "OCLC-MARC Transaction Code";
    mqTag = "OCLCMARCTransactionCode";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "http://www.oclc.org/bibformats/en/9xx/994.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Transaction code", "NR",
      "b", "Institution symbol", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
    getSubfield("b").setMqTag("institutionSymbol");
  }
}
