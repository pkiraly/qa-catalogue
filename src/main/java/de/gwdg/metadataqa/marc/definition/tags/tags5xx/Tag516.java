package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Type of Computer File or Data Note
 * http://www.loc.gov/marc/bibliographic/bd516.html
 */
public class Tag516 extends DataFieldDefinition {

  private static Tag516 uniqueInstance;

  private Tag516() {
    initialize();
    postCreation();
  }

  public static Tag516 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag516();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "516";
    label = "Type of Computer File or Data Note";
    mqTag = "TypeOfComputerFileOrData";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd516.html";

    ind1 = new Indicator("Display constant controller")
      .setCodes(
        " ", "Type of file",
        "8", "No display constant generated"
      )
      .setMqTag("displayConstant")
      .setFrbrFunctions(ManagementDisplay);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Type of computer file or data note", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySelect);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
  }
}
