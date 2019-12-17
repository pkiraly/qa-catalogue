package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ResourceActionTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Action Note
 * http://www.loc.gov/marc/bibliographic/bd583.html
 */
public class Tag583 extends DataFieldDefinition {

  private static Tag583 uniqueInstance;

  private Tag583() {
    initialize();
    postCreation();
  }

  public static Tag583 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag583();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "583";
    label = "Action Note";
    mqTag = "Action";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd583.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Privacy")
      .setCodes(
        " ", "No information provided",
        "0", "Private",
        "1", "Not private"
      )
      .setMqTag("privacy");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Action", "NR",
      "b", "Action identification", "R",
      "c", "Time/date of action", "R",
      "d", "Action interval", "R",
      "e", "Contingency for action", "R",
      "f", "Authorization", "R",
      "h", "Jurisdiction", "R",
      "i", "Method of action", "R",
      "j", "Site of action", "R",
      "k", "Action agent", "R",
      "l", "Status", "R",
      "n", "Extent", "R",
      "o", "Type of unit", "R",
      "u", "Uniform Resource Identifier", "R",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(ResourceActionTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("identification")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("timeOrDate")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("interval")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("contingency")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("authorization")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("jurisdiction")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("method")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("site")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setBibframeTag("agent")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setBibframeTag("status")
      .setFrbrFunctions(DiscoverySelect, UseManage)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("extent")
      .setCompilanceLevels("A");

    getSubfield("o")
      .setMqTag("typeOfUnit")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("x")
      .setMqTag("nonpublicNote")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("z")
      .setBibframeTag("publicNote")
      .setFrbrFunctions(UseManage)
      .setCompilanceLevels("A");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");
  }
}
