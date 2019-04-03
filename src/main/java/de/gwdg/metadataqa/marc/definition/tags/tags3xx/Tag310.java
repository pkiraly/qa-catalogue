package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Current Publication Frequency
 * http://www.loc.gov/marc/bibliographic/bd310.html
 */
public class Tag310 extends DataFieldDefinition {
  private static Tag310 uniqueInstance;

  private Tag310() {
    initialize();
    postCreation();
  }

  public static Tag310 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag310();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "310";
    label = "Current Publication Frequency";
    bibframeTag = "Frequency";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd310.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Current publication frequency", "NR",
      "b", "Date of current publication frequency", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UsageManage);
    getSubfield("b").setBibframeTag("date")
      .setFrbrFunctions(UsageManage);
    getSubfield("6").setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess) ;
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess) ;
  }
}
