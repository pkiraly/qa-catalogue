package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Terms Governing Use and Reproduction Note
 * http://www.loc.gov/marc/bibliographic/bd540.html
 */
public class Tag540 extends DataFieldDefinition {

  private static Tag540 uniqueInstance;

  private Tag540() {
    initialize();
    postCreation();
  }

  public static Tag540 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag540();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "540";
    label = "Terms Governing Use and Reproduction Note";
    bibframeTag = "UsePolicy";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd540.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Terms governing use and reproduction", "NR",
      "b", "Jurisdiction", "NR",
      "c", "Authorization", "NR",
      "d", "Authorized users", "NR",
      "u", "Uniform Resource Identifier", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(UsageRestrict);
    getSubfield("b").setMqTag("jurisdiction")
      .setFrbrFunctions(UsageRestrict);
    getSubfield("c").setBibframeTag("source")
      .setFrbrFunctions(UsageRestrict);
    getSubfield("d").setMqTag("authorizedUsers")
      .setFrbrFunctions(UsageRestrict);
    getSubfield("u").setBibframeTag("rdfs:label").setMqTag("uri")
      .setFrbrFunctions(DiscoverySelect);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
