package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Dates of Publication and/or Sequential Designation
 * http://www.loc.gov/marc/bibliographic/bd362.html
 */
public class Tag362 extends DataFieldDefinition {
  private static Tag362 uniqueInstance;

  private Tag362() {
    initialize();
    postCreation();
  }

  public static Tag362 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag362();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "362";
    label = "Dates of Publication and/or Sequential Designation";
    mqTag = "DatesOfPublication";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd362.html";
    setLevels("A", "A");

    ind1 = new Indicator("Format of date")
      .setCodes(
        "0", "Formatted style",
        "1", "Unformatted note"
      )
      .setMqTag("format")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Dates of publication and/or sequential designation", "NR",
      "z", "Source of information", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setLevels("M", "M");

    getSubfield("z")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");

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
