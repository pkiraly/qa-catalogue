package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Awards Note
 * http://www.loc.gov/marc/bibliographic/bd586.html
 */
public class Tag586 extends DataFieldDefinition {

  private static Tag586 uniqueInstance;

  private Tag586() {
    initialize();
    postCreation();
  }

  public static Tag586 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag586();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "586";
    label = "Awards Note";
    bibframeTag = "Awards";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd586.html";

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Awards",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Awards note", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("M");

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
