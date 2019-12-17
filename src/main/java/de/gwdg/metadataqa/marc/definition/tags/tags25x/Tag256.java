package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Computer File Characteristics
 * http://www.loc.gov/marc/bibliographic/bd256.html
 */
public class Tag256 extends DataFieldDefinition {
  private static Tag256 uniqueInstance;

  private Tag256() {
    initialize();
    postCreation();
  }

  public static Tag256 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag256();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "256";
    label = "Computer File Characteristics";
    mqTag = "ComputerFile";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd256.html";
    setLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Computer file characteristics", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate)
      .setLevels("M", "M");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
