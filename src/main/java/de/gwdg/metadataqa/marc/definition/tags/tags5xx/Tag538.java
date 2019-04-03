package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
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

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect, UsageOperate);
    getSubfield("i").setMqTag("displayText")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, ManagementIdentify);
    getSubfield("u").setBibframeTag("rdfs:label").setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, ManagementIdentify);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify, ManagementIdentify, ManagementProcess);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
