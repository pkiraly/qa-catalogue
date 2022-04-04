package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * System Details Note
 * http://www.loc.gov/marc/bibliographic/bd538.html
 */
public class Tag538 extends DataFieldDefinition {

  private static Tag538 uniqueInstance;

  private Tag538() {
    initialize();
    postCreation();
  }

  public static Tag538 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag538();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "538";
    label = "System Details Note";
    bibframeTag = "SystemRequirement";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd538.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "System details note", "NR",
      "i", "Display text", "NR",
      "u", "Uniform Resource Identifier", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect, UseOperate)
      .setCompilanceLevels("M");

    getSubfield("i")
      .setMqTag("displayText")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setBibframeTag("rdfs:label").setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify, ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setCompilanceLevels("A");

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
