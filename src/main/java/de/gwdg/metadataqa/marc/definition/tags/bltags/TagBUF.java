package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Batch Upgrade Flag
 */
public class TagBUF extends DataFieldDefinition {

  private static TagBUF uniqueInstance;

  private TagBUF() {
    initialize();
    postCreation();
  }

  public static TagBUF getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagBUF();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "BUF";
    label = "Batch Upgrade Flag";
    mqTag = "batchUpgradeFlag";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator("Batch Upgrade Process")
      .setCodes(
        " ", "Categories 1-3 (Legal Deposit/Purchased Print Monographs)",
        "1", "eBooks",
        "2", "Western European Print Monographs"
      )
      .setMqTag("batchUpgradeProcess");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Codes", "NR",
      "d", "Date", "NR"
    );

    getSubfield("a")
      .setCodes(
        "Y", "Awaiting batch upgrade (Category 1)",
        "N", "Batch upgraded (Category 1)",
        "Y2", "Awaiting batch upgrade (Category 2)",
        "N2", "Batch upgraded (Western European)",
        "Y3", "Awaiting batch upgrade (Category 3)",
        "N3", "Batch upgraded (Category 3)",
        "YM", "Awaiting batch upgrade (Printed Music)",
        "NM", "Batch upgraded (Printed Music)",
        "R", "Upgraded manually (Categories 1-3)",
        "RM", "Upgraded manually (Printed Music)"
      )
      .setMqTag("codes");

    getSubfield("d")
      .setValidator(new RegexValidator("^\\d{4}(0[1-9]|1[0-2])([0-2][0-9]|3[0-1])$"))
      .setMqTag("date");
  }
}
