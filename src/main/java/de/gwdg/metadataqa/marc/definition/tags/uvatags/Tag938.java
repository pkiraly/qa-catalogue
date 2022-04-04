package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Vendor specific ordering data (defined by OCLC)
 */
public class Tag938 extends DataFieldDefinition {
  private static Tag938 uniqueInstance;

  private Tag938() {
    initialize();
    postCreation();
  }

  public static Tag938 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag938();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "938";
    label = "Vendor specific ordering data (defined by OCLC)";
    mqTag = "VendorSpecificOrderingData";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "OCLC-defined symbol for vendor", "NR",
      "b", "Vendor control number", "NR"
    );

    getSubfield("a").setMqTag("OCLCDefinedSymbolForVendor");
    getSubfield("b").setMqTag("VendorControlNumber");
  }
}
