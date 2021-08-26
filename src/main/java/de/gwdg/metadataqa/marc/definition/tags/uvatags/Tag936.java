package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * CONSER/OCLC Miscellaneous Data
 */
public class Tag936 extends DataFieldDefinition {
  private static Tag936 uniqueInstance;

  private Tag936() {
    initialize();
    postCreation();
  }

  public static Tag936 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag936();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "936";
    label = "CONSER/OCLC Miscellaneous Data";
    mqTag = "CONSEROCLCMiscellaneousData";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "CONSER/OCLC miscellaneous data", "R"
    );

    getSubfield("a").setMqTag("CONSEROCLCMiscellaneousData");
  }
}
