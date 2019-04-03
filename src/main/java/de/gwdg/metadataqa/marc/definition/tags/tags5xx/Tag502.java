package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Dissertation Note
 * http://www.loc.gov/marc/bibliographic/bd502.html
 */
public class Tag502 extends DataFieldDefinition {

  private static Tag502 uniqueInstance;

  private Tag502() {
    initialize();
    postCreation();
  }

  public static Tag502 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag502();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "502";
    label = "Dissertation Note";
    bibframeTag = "Dissertation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd502.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Dissertation note", "NR",
      "b", "Degree type", "NR",
      "c", "Name of granting institution", "NR",
      "d", "Year degree granted", "NR",
      "g", "Miscellaneous information", "R",
      "o", "Dissertation identifier", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain);
    getSubfield("b").setBibframeTag("degree");
    getSubfield("c").setBibframeTag("grantingInstitution");
    getSubfield("d").setBibframeTag("date");
    getSubfield("g").setBibframeTag("note");
    getSubfield("o").setBibframeTag("identifiedBy");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
