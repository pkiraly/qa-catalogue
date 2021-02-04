package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Added Entry - Uncontrolled Name
 * http://www.loc.gov/marc/bibliographic/bd720.html
 */
public class Tag720 extends DataFieldDefinition {

  private static Tag720 uniqueInstance;

  private Tag720() {
    initialize();
    postCreation();
  }

  public static Tag720 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag720();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "720";
    label = "Added Entry - Uncontrolled Name";
    mqTag = "UncontrolledName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd720.html";
    setCompilanceLevels("A");

    ind1 = new Indicator("Type of name")
      .setCodes(
        " ", "Not specified",
        "1", "Personal",
        "2", "Other"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name", "NR",
      "e", "Relator term", "R",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("e")
      .setMqTag("relator")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("4")
      .setMqTag("relationship")
      .setCodeList(RelatorCodes.getInstance())
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

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
