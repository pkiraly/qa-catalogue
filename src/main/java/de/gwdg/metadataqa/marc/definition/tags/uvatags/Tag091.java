package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

/**
 * Abbreviated Title
 * http://www.loc.gov/marc/bibliographic/bd210.html
 */
public class Tag091 extends DataFieldDefinition {
  private static Tag091 uniqueInstance;

  private Tag091() {
    initialize();
    postCreation();
  }

  public static Tag091 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag091();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "091";
    label = "Local Classification Codes (Controlled)";
    mqTag = "LocalClassificationCodesControlled";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = null;
    ind2 = null;

    setSubfieldsWithCardinality(
      "a", "General Classification Codes", "NR",
      "b", "Classification Codes Rosenthaliana", "NR",
      "c", "Local NLM Classification Codes HvA", "NR",
      "d", "Local SISO Classification Codes HvA", "NR",
      "e", "Local J-classification Codes HvA", "NR"
    );

    getSubfield("a").setMqTag("GeneralClassificationCodes");
    getSubfield("b").setMqTag("ClassificationCodesRosenthaliana");
    getSubfield("c").setMqTag("LocalNLMClassificationCodesHvA");
    getSubfield("d").setMqTag("LocalSISOClassificationCodesHvA");
    getSubfield("e").setMqTag("LocalJclassificationCodesHvA");
  }
}
