package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.AccessRestrictionTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Restrictions on Access Note
 * http://www.loc.gov/marc/bibliographic/bd506.html
 */
public class Tag506 extends DataFieldDefinition {

  private static Tag506 uniqueInstance;

  private Tag506() {
    initialize();
    postCreation();
  }

  public static Tag506 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag506();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "506";
    label = "Restrictions on Access Note";
    bibframeTag = "UsageAndAccessPolicy";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd506.html";
    setLevels("O");

    ind1 = new Indicator("Restriction")
      .setCodes(
        " ", "No information provided",
        "0", "No restrictions",
        "1", "Restrictions apply"
      )
      .setMqTag("restriction");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Terms governing access", "NR",
      "b", "Jurisdiction", "R",
      "c", "Physical access provisions", "R",
      "d", "Authorized users", "R",
      "e", "Authorization", "R",
      "f", "Standardized terminology for access restriction", "R",
      "g", "Availability date", "R",
      "q", "Supplying agency", "NR",
      "u", "Uniform Resource Identifier", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(AccessRestrictionTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(UseRestrict)
      .setLevels("M");

    getSubfield("b")
      .setMqTag("jurisdiction")
      .setFrbrFunctions(UseRestrict)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("physicalAccessProvisions")
      .setFrbrFunctions(UseRestrict)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("authorizedUsers")
      .setFrbrFunctions(UseRestrict)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("authorization")
      .setFrbrFunctions(UseRestrict)
      .setLevels("A");

    getSubfield("f")
      .setMqTag("terminology")
      .setLevels("O");

    getSubfield("g")
      .setMqTag("availabilityDate");

    getSubfield("q")
      .setMqTag("supplyingAgency");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryObtain)
      .setLevels("O");

    getSubfield("2")
      .setMqTag("source")
      .setLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setLevels("A");

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
