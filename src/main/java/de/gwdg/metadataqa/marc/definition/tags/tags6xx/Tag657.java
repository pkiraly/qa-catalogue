package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.FunctionTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Index Term - Function
 * http://www.loc.gov/marc/bibliographic/bd657.html
 */
public class Tag657 extends DataFieldDefinition {

  private static Tag657 uniqueInstance;

  private Tag657() {
    initialize();
    postCreation();
  }

  public static Tag657 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag657();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "657";
    label = "Index Term - Function";
    mqTag = "Function";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd657.html";

    ind1 = new Indicator();

    ind2 = new Indicator("Source of term")
      .setCodes(
        "7", "Source specified in subfield $2"
      )
      .setMqTag("sourceOfTerm")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Function", "NR",
      "v", "Form subdivision", "R",
      "x", "General subdivision", "R",
      "y", "Chronological subdivision", "R",
      "z", "Geographic subdivision", "R",
      "0", "Authority record control number", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(FunctionTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
