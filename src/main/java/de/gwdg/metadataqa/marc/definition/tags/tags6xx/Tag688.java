package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Subject Added Entry - Type of Entity Unspecified
 * https://www.loc.gov/marc/bibliographic/bd688.html
 */
public class Tag688 extends DataFieldDefinition {

  private static Tag688 uniqueInstance;

  private Tag688() {
    initialize();
    postCreation();
  }

  public static Tag688 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag688();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "688";
    label = "Subject Added Entry - Type of Entity Unspecified";
    bibframeTag = "TypeOfEntityUnspecified";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd688.html";

    ind1 = new Indicator();
    ind2 = new Indicator("Source of name, title, or term")
      .setCodes(
        " ", "No information provided",
        "7", "Source specified in subfield $2"
      )
      .setMqTag("sourceOfName");

    setSubfieldsWithCardinality(
      "a", "Name, title, or term", "NR",
      "e", "Relator term", "R",
      "g", "Miscellaneous information", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source of name, title, or term", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("nameTitleOrTerm");

    getSubfield("e")
      .setBibframeTag("relator");

    getSubfield("g")
      .setBibframeTag("region");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance())
      .setCompilanceLevels("O");

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("4")
      .setMqTag("relationship")
      .setCodeList(RelatorCodes.getInstance());

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("8")
      .setMqTag("fieldLink");


    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
