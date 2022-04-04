package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Classification Codes (Controlled)
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
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

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
