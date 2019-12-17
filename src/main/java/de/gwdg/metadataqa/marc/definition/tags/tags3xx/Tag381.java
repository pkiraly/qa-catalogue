package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Other Distinguishing Characteristics of Work or Expression
 * http://www.loc.gov/marc/bibliographic/bd381.html
 */
public class Tag381 extends DataFieldDefinition {
  private static Tag381 uniqueInstance;

  private Tag381() {
    initialize();
    postCreation();
  }

  public static Tag381 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag381();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "381";
    label = "Other Distinguishing Characteristics of Work or Expression";
    mqTag = "OtherDistinguishingCharacteristics";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd381.html";
    setLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Other distinguishing characteristic", "R",
      "u", "Uniform Resource Identifier", "R",
      "v", "Source of information", "R",
      "0", "Record control number", "R",
      "2", "Source of term", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setLevels("M");

    getSubfield("u")
      .setMqTag("uri")
      .setLevels("A");

    getSubfield("v")
      .setMqTag("sourceOfInformation")
      .setLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setLevels("A");

    getSubfield("2")
      .setMqTag("source")
      .setLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setLevels("O");
  }
}
