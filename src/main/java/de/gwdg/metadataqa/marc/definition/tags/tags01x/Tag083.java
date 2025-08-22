package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Additional Dewey Decimal Classification Number
 * https://www.loc.gov/marc/bibliographic/bd083.html
 */
public class Tag083 extends DataFieldDefinition {

  private static Tag083 uniqueInstance;

  private Tag083() {
    initialize();
    postCreation();
  }

  public static Tag083 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag083();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "083";
    label = "Additional Dewey Decimal Classification Number";
    mqTag = "ClassificationAdditionalDdc";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd083.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Type of edition")
      .setCodes(
        "0", "Full edition",
        "1", "Abridged edition",
        "7", "Other edition specified in subfield $2"
      )
      .setMqTag("editionType");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Classification number", "R",
      "c", "Classification number--Ending number of span", "R",
      "m", "Standard or optional designation", "NR",
      "q", "Assigning agency", "NR",
      "y", "Table sequence number for internal subarrangement or add table", "R",
      "z", "Table identification", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Edition information", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("q").setCodeList(OrganizationCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setCompilanceLevels("M");

    getSubfield("c")
      .setMqTag("endingNumber")
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("standard")
      .setCompilanceLevels("M");

    getSubfield("q")
      .setMqTag("source")
      .setCompilanceLevels("O");

    getSubfield("y")
      .setMqTag("tableSequenceNumber")
      .setCompilanceLevels("A");

    getSubfield("z")
      .setMqTag("tableId");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("edition")
      .setCompilanceLevels("M");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("dataProvenance");

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
