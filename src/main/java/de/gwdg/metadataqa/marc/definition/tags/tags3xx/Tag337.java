package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Media Type
 * http://www.loc.gov/marc/bibliographic/bd337.html
 */
public class Tag337 extends DataFieldDefinition {
  private static Tag337 uniqueInstance;

  private Tag337() {
    initialize();
    postCreation();
  }

  public static Tag337 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag337();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "337";
    label = "Media Type";
    bibframeTag = "Media";
    mqTag = "MediaType";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd337.html";
    setLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Media type term", "R",
      "b", "Media type code", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setLevels("M");

    getSubfield("b")
      .setMqTag("mediaTypeCode")
      .setLevels("M");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber");

    getSubfield("2")
      .setBibframeTag("source")
      .setLevels("M");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setLevels("O");
  }
}
