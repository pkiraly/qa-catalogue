package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

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
    setCompilanceLevels("O");

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
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("contract")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("grant")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("undifferentiatedNumber")
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("program")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("project")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("tast")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("workUnit")
      .setFrbrFunctions(DiscoveryIdentify)
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
