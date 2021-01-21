package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Shared Library Message Field
 */
public class Tag997 extends DataFieldDefinition {

  private static Tag997 uniqueInstance;

  private Tag997() {
    initialize();
    postCreation();
  }

  public static Tag997 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag997();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "997";
    label = "Shared Library Message Field";
    mqTag = "SharedLibraryMessageField";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Legal Deposit Library’s message", "R"
    );

    getSubfield("a")
      .setMqTag("legalDepositLibrarysMessage");
  }
}
