package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Location of Other Archival Materials Note
 * http://www.loc.gov/marc/bibliographic/bd544.html
 */
public class Tag544 extends DataFieldDefinition {

  private static Tag544 uniqueInstance;

  private Tag544() {
    initialize();
    postCreation();
  }

  public static Tag544 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag544();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "544";
    label = "Location of Other Archival Materials Note";
    mqTag = "LocationOfOtherArchivalMaterials";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd544.html";
    setLevels("O");

    ind1 = new Indicator("Relationship")
      .setCodes(
        " ", "No information provided",
        "0", "Associated materials",
        "1", "Related materials"
      )
      .setMqTag("relationship");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Custodian", "R",
      "b", "Address", "R",
      "c", "Country", "R",
      "d", "Title", "R",
      "e", "Provenance", "R",
      "n", "Note", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("custodian")
      .setFrbrFunctions(DiscoveryObtain)
      .setLevels("M");

    getSubfield("b")
      .setMqTag("address")
      .setFrbrFunctions(DiscoveryObtain)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("country")
      .setFrbrFunctions(DiscoveryObtain)
      .setLevels("O");

    getSubfield("d")
      .setMqTag("title")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("provenance")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("n")
      .setMqTag("note")
      .setLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
