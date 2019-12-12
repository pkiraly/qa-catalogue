package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Normalized Date and Sequential Designation
 * http://www.loc.gov/marc/bibliographic/bd363.html
 */
public class Tag363 extends DataFieldDefinition {
  private static Tag363 uniqueInstance;

  private Tag363() {
    initialize();
    postCreation();
  }

  public static Tag363 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag363();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "363";
    label = "Normalized Date and Sequential Designation";
    mqTag = "NormalizedDateAndSequentialDesignation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd363.html";

    ind1 = new Indicator("Start/End designator")
      .setCodes(
        " ", "No information provided",
        "0", "Starting information",
        "1", "Ending information"
      )
      .setMqTag("startOrEndDesignator");

    ind2 = new Indicator("State of issuance")
      .setCodes(
        " ", "Not specified",
        "0", "Closed",
        "1", "Open"
      )
      .setMqTag("stateOfIssuance");

    setSubfieldsWithCardinality(
      "a", "First level of enumeration", "NR",
      "b", "Second level of enumeration", "NR",
      "c", "Third level of enumeration", "NR",
      "d", "Fourth level of enumeration", "NR",
      "e", "Fifth level of enumeration", "NR",
      "f", "Sixth level of enumeration", "NR",
      "g", "Alternative numbering scheme, first level of enumeration", "NR",
      "h", "Alternative numbering scheme, second level of enumeration", "NR",
      "i", "First level of chronology", "NR",
      "j", "Second level of chronology", "NR",
      "k", "Third level of chronology", "NR",
      "l", "Fourth level of chronology", "NR",
      "m", "Alternative numbering scheme, chronology", "NR",
      "u", "First level textual designation", "NR",
      "v", "First level of chronology, issuance", "NR",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("firstLevel")
      .setLevels("A");

    getSubfield("b")
      .setMqTag("secondLevel")
      .setLevels("A");

    getSubfield("c")
      .setMqTag("thirdLevel")
      .setLevels("A");

    getSubfield("d")
      .setMqTag("fourthLevel")
      .setLevels("A");

    getSubfield("e")
      .setMqTag("fifthLevel")
      .setLevels("A");

    getSubfield("f")
      .setMqTag("sixthLevel")
      .setLevels("A");

    getSubfield("g")
      .setMqTag("alternativeFirstLevel")
      .setLevels("O");

    getSubfield("h")
      .setMqTag("alternativeSecondLevel")
      .setLevels("O");

    getSubfield("i")
      .setMqTag("firstLevelOfChronology")
      .setLevels("A");

    getSubfield("j")
      .setMqTag("secondLevelOfChronology")
      .setLevels("A");

    getSubfield("k")
      .setMqTag("thirdLevelOfChronology")
      .setLevels("A");

    getSubfield("l")
      .setMqTag("fourthLevelOfChronology")
      .setLevels("A");

    getSubfield("m")
      .setMqTag("alternativeNumberingAndChronology")
      .setLevels("O");

    getSubfield("u")
      .setMqTag("firstLevelTextualDesignation")
      .setLevels("A");

    getSubfield("v")
      .setMqTag("firstLevelOfChronologyIssuance")
      .setLevels("A");

    getSubfield("x")
      .setMqTag("nonPublicNote")
      .setLevels("O");

    getSubfield("z")
      .setMqTag("publicNote")
      .setLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setLevels("O");
  }
}
