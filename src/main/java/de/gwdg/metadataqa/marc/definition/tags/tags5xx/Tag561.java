package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Ownership and Custodial History
 * http://www.loc.gov/marc/bibliographic/bd561.html
 */
public class Tag561 extends DataFieldDefinition {

  private static Tag561 uniqueInstance;

  private Tag561() {
    initialize();
    postCreation();
  }

  public static Tag561 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag561();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "561";
    label = "Ownership and Custodial History";
    mqTag = "CustodialHistory";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd561.html";

    ind1 = new Indicator("Privacy")
      .setCodes(
        " ", "No information provided",
        "0", "Private",
        "1", "Not private"
      )
      .setMqTag("privacy");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "History", "NR",
      "u", "Uniform Resource Identifier", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("custodialHistory").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("u").setMqTag("uri");
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);

    setHistoricalSubfields(
      "b", "Time of collation (NR) [OBSOLETE, 1997]"
    );
  }
}
