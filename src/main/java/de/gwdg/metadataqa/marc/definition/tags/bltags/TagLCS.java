package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Library of Congress Series Statement
 */
public class TagLCS extends DataFieldDefinition {

  private static TagLCS uniqueInstance;

  private TagLCS() {
    initialize();
    postCreation();
  }

  public static TagLCS getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagLCS();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "LCS";
    label = "Library of Congress Series Statement";
    mqTag = "LoCSeriesStatement";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    // TODO: I expect it as a binary value
    ind1 = new Indicator("Specifies whether series is traced")
      .setCodes("0", "Series not traced")
      .setMqTag("seriesIsTraced");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Series statement", "R",
      "l", "Library of Congress call number", "NR",
      "v", "Volume/sequential designation", "R",
      "x", "International Standard Serial Number", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      // .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      // .setCompilanceLevels("M", "M")
    ;

    getSubfield("l")
      .setMqTag("lccn")
      // .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      // .setCompilanceLevels("A")
    ;

    getSubfield("v")
      .setMqTag("volume")
      // .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      // .setCompilanceLevels("A", "A")
    ;

    getSubfield("x")
      .setMqTag("issn")
      // .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      // .setCompilanceLevels("A", "A")
    ;

    getSubfield("3")
      .setMqTag("materialsSpecified")
      // .setCompilanceLevels("O")
    ;

    getSubfield("6")
      .setBibframeTag("linkage")
      // .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      // .setCompilanceLevels("A", "A")
    ;

    getSubfield("8")
      .setMqTag("fieldLink")
      // .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      // .setCompilanceLevels("O")
    ;
  }
}
