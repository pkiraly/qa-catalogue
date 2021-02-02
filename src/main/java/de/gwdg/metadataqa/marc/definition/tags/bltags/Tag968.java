package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Record Status Field
 */
public class Tag968 extends DataFieldDefinition {

  private static Tag968 uniqueInstance;

  private Tag968() {
    initialize();
    postCreation();
  }

  public static Tag968 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag968();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "968";
    label = "Record Status Field";
    mqTag = "RecordStatusField";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Record status", "R",
      "b", "Record status", "R",
      "c", "Record status", "R"
    );

    getSubfield("a")
      .setCodes(
        "C", "Retrospectively converted STM record.",
        "D", "Used when there is some doubt about information in the record.",
        "R", "Retrospectively converted STM record.",
        "T", "Record based on title page."
      )
      .setMqTag("recordStatus");

    getSubfield("b")
      .setCodes(
        "x", "x"
      )
      .setMqTag("recordStatus");

    getSubfield("c")
      .setCodes(
        "BR", "BR"
      )
      .setMqTag("recordStatus");
  }
}
