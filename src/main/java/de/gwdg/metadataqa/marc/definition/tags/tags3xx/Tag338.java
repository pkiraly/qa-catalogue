package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

/**
 * Carrier Type
 * http://www.loc.gov/marc/bibliographic/bd338.html
 */
public class Tag338 extends DataFieldDefinition {
  private static Tag338 uniqueInstance;

  private Tag338() {
    initialize();
    postCreation();
  }

  public static Tag338 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag338();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "338";
    label = "Carrier Type";
    bibframeTag = "Carrier";
    mqTag = "CarrierType";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd338.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Carrier type term", "R",
      "b", "Carrier type code", "R",
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
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("carrierTypeCode")
      .setCompilanceLevels("M");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber");

    getSubfield("2")
      .setBibframeTag("source")
      .setCompilanceLevels("M");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));
  }
}
