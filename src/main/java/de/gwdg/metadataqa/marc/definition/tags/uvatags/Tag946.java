package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Processing Information
 */
public class Tag946 extends DataFieldDefinition {
  private static Tag946 uniqueInstance;

  private Tag946() {
    initialize();
    postCreation();
  }

  public static Tag946 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag946();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "946";
    label = "Local Processing Information";
    mqTag = "LocalProcessingInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Date delivered", "NR",
      "b", "Transaction type", "NR",
      "c", "Reason for Record Output", "NR"
    );

    getSubfield("a").setMqTag("DateDelivered");
    getSubfield("b").setMqTag("TransactionType");
    getSubfield("c").setMqTag("ReasonForRecordOutput");
  }
}
