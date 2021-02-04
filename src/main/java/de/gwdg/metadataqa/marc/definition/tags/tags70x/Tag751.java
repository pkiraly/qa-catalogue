package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;

import java.util.Arrays;

/**
 * Added Entry - Geographic Name
 * http://www.loc.gov/marc/bibliographic/bd751.html
 */
public class Tag751 extends DataFieldDefinition {

  private static Tag751 uniqueInstance;

  private Tag751() {
    initialize();
    postCreation();
  }

  public static Tag751 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag751();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "751";
    label = "Added Entry - Geographic Name";
    mqTag = "AddedGeographicName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd751.html";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Geographic name", "NR",
      "e", "Relator term", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source of heading or term", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setCompilanceLevels("M");

    getSubfield("e")
      .setMqTag("relator")
      .setCompilanceLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance())
      .setCompilanceLevels("O");

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setCompilanceLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setCompilanceLevels("O");

    getSubfield("4")
      .setMqTag("relationship")
      .setCodeList(RelatorCodes.getInstance())
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR"),
      new SubfieldDefinition("9", "NKCR Authority field - tracing form", "NR")
    ));

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
