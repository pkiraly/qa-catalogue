package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Citation/References Note
 * http://www.loc.gov/marc/bibliographic/bd510.html
 */
public class Tag510 extends DataFieldDefinition {

  private static Tag510 uniqueInstance;

  private Tag510() {
    initialize();
    postCreation();
  }

  public static Tag510 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag510();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "510";
    label = "Citation/References Note";
    mqTag = "Citation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd510.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Coverage/location in source")
      .setCodes(
        "0", "Coverage unknown",
        "1", "Coverage complete",
        "2", "Coverage is selective",
        "3", "Location in source not given",
        "4", "Location in source given"
      )
      .setMqTag("coverageOrLocationInSource");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name of source", "NR",
      "b", "Coverage of source", "NR",
      "c", "Location within source", "NR",
      "u", "Uniform Resource Identifier", "R",
      "x", "International Standard Serial Number", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("a")
      .setMqTag("name")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("coverage")
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("location")
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setCompilanceLevels("O");

    getSubfield("x")
      .setMqTag("issn")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
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
