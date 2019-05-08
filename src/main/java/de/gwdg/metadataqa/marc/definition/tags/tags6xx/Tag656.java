package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OccupationTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Index Term - Occupation
 * http://www.loc.gov/marc/bibliographic/bd656.html
 */
public class Tag656 extends DataFieldDefinition {

  private static Tag656 uniqueInstance;

  private Tag656() {
    initialize();
    postCreation();
  }

  public static Tag656 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag656();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "656";
    label = "Index Term - Occupation";
    bibframeTag = "ComplexSubject";
    mqTag = "Occupation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd656.html";

    ind1 = new Indicator();

    ind2 = new Indicator("Source of term")
      .setCodes(
        "7", "Source specified in subfield $2"
      )
      .setMqTag("sourceOfTerm")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Occupation", "NR",
      "k", "Form", "NR",
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

    getSubfield("2").setCodeList(OccupationTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("occupation")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("k").setBibframeTag("genreForm").setMqTag("form")
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
