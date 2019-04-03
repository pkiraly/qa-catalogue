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

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(UsageManage);
    getSubfield("b").setMqTag("identification")
      .setFrbrFunctions(UsageManage);
    getSubfield("c").setMqTag("timeOrDate")
      .setFrbrFunctions(UsageManage);
    getSubfield("d").setMqTag("interval")
      .setFrbrFunctions(UsageManage);
    getSubfield("e").setMqTag("contingency")
      .setFrbrFunctions(UsageManage);
    getSubfield("f").setMqTag("authorization")
      .setFrbrFunctions(UsageManage);
    getSubfield("h").setMqTag("jurisdiction")
      .setFrbrFunctions(UsageManage);
    getSubfield("i").setMqTag("method")
      .setFrbrFunctions(UsageManage);
    getSubfield("j").setMqTag("site")
      .setFrbrFunctions(UsageManage);
    getSubfield("k").setBibframeTag("agent")
      .setFrbrFunctions(UsageManage);
    getSubfield("l").setBibframeTag("status")
      .setFrbrFunctions(DiscoverySelect, UsageManage);
    getSubfield("n").setMqTag("extent");
    getSubfield("o").setMqTag("typeOfUnit")
      .setFrbrFunctions(UsageManage);
    getSubfield("u").setMqTag("uri")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("x").setMqTag("nonpublicNote")
      .setFrbrFunctions(UsageManage);
    getSubfield("z").setBibframeTag("publicNote")
      .setFrbrFunctions(UsageManage);
    getSubfield("2").setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
