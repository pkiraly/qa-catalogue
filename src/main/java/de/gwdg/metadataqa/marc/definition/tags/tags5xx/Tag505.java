package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Formatted Contents Note
 * http://www.loc.gov/marc/bibliographic/bd505.html
 */
public class Tag505 extends DataFieldDefinition {

  private static Tag505 uniqueInstance;

  private Tag505() {
    initialize();
    postCreation();
  }

  public static Tag505 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag505();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "505";
    label = "Formatted Contents Note";
    bibframeTag = "TableOfContents";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd505.html";
    setLevels("O");

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        "0", "Contents",
        "1", "Incomplete contents",
        "2", "Partial contents",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator("Level of content designation")
      .setCodes(
        " ", "Basic",
        "0", "Enhanced"
      )
      .setMqTag("level")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    setSubfieldsWithCardinality(
      "a", "Formatted contents note", "NR",
      "g", "Miscellaneous information", "R",
      "r", "Statement of responsibility", "R",
      "t", "Title", "R",
      "u", "Uniform Resource Identifier", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("g")
      .setMqTag("miscellaneous")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("r")
      .setMqTag("responsibility")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("t")
      .setMqTag("title")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryObtain)
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
