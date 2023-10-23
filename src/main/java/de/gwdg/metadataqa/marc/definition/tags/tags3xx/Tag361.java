package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

/**
 * Structured Ownership and Custodial History
 * http://www.loc.gov/marc/bibliographic/bd361.html
 */
public class Tag361 extends DataFieldDefinition {
  private static Tag361 uniqueInstance;

  private Tag361() {
    initialize();
    postCreation();
  }

  public static Tag361 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag361();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "361";
    label = "Structured Ownership and Custodial History";
    mqTag = "StructuredOwnershipAndCustodialHistory";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd361.html";

    ind1 = new Indicator("Privacy")
      .setCodes(
        " ", "No information provided",
        "0", "Private",
        "1", "Not private"
      )
      .setMqTag("privacy");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Name", "NR",
      "f", "Ownership and custodial history evidence term", "R",
      "k", "Formatted date", "NR",
      "l", "Date", "NR",
      "o", "Type of ownership and custodial history information", "R",
      "s", "Shelf mark of copy described", "NR",
      "u", "Uniform Resource Identifier", "R",
      "x", "Nonpublic note", "R",
      "y", "Identifier of the copy described", "NR",
      "z", "Public note", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("name");

    getSubfield("f")
      .setMqTag("term");

    getSubfield("k")
      .setMqTag("formattedDate");

    getSubfield("l")
      .setMqTag("date");

    getSubfield("o")
      .setMqTag("type");

    getSubfield("s")
      .setMqTag("shelfMark");

    getSubfield("u")
      .setMqTag("uri");

    getSubfield("x")
      .setMqTag("nonpublicNote");

    getSubfield("y")
      .setMqTag("identifierOfTheCopy");

    getSubfield("z")
      .setMqTag("publicNote");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
