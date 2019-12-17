package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Funding Information Note
 * http://www.loc.gov/marc/bibliographic/bd536.html
 */
public class Tag536 extends DataFieldDefinition {

  private static Tag536 uniqueInstance;

  private Tag536() {
    initialize();
    postCreation();
  }

  public static Tag536 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag536();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "536";
    label = "Funding Information Note";
    bibframeTag = "FundingInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd536.html";
    setLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Text of note", "NR",
      "b", "Contract number", "R",
      "c", "Grant number", "R",
      "d", "Undifferentiated number", "R",
      "e", "Program element number", "R",
      "f", "Project number", "R",
      "g", "Task number", "R",
      "h", "Work unit number", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setLevels("A");

    getSubfield("b")
      .setMqTag("contract")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("grant")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("undifferentiatedNumber")
      .setLevels("A");

    getSubfield("e")
      .setMqTag("program")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("f")
      .setMqTag("project")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("g")
      .setMqTag("tast")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("h")
      .setMqTag("workUnit")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

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
