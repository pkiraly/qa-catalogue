package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

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
    setCompilanceLevels("O");

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
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("address")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("country")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("d")
      .setMqTag("title")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("provenance")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("n")
      .setMqTag("note")
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
