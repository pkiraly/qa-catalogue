package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

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
    setCompilanceLevels("O");

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
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("secondLevel")
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("thirdLevel")
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("fourthLevel")
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("fifthLevel")
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("sixthLevel")
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("alternativeFirstLevel")
      .setCompilanceLevels("O");

    getSubfield("h")
      .setMqTag("alternativeSecondLevel")
      .setCompilanceLevels("O");

    getSubfield("i")
      .setMqTag("firstLevelOfChronology")
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("secondLevelOfChronology")
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("thirdLevelOfChronology")
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("fourthLevelOfChronology")
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("alternativeNumberingAndChronology")
      .setCompilanceLevels("O");

    getSubfield("u")
      .setMqTag("firstLevelTextualDesignation")
      .setCompilanceLevels("A");

    getSubfield("v")
      .setMqTag("firstLevelOfChronologyIssuance")
      .setCompilanceLevels("A");

    getSubfield("x")
      .setMqTag("nonPublicNote")
      .setCompilanceLevels("O");

    getSubfield("z")
      .setMqTag("publicNote")
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
