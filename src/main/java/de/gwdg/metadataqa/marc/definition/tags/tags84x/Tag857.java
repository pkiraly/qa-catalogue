package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.ElectronicAccessMethodsCodeList;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;

/**
 * Electronic Archive Location and Access
 * https://www.loc.gov/marc/bibliographic/bd857.html
 */
public class Tag857 extends DataFieldDefinition {

  private static Tag857 uniqueInstance;

  private Tag857() {
    initialize();
    postCreation();
  }

  public static Tag857 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag857();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "857";
    label = "Electronic Archive Location and Access";
    mqTag = "ElectronicArchiveLocationAndAccess";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd857.html";

    ind1 = new Indicator("Access method")
      .setCodes(
        " ", "No information provided",
        "1", "FTP",
        "4", "HTTP",
        "7", "Method specified in subfield $2"
      )
      .setMqTag("accessMethod")
      .setFrbrFunctions(DiscoveryObtain);

    ind2 = new Indicator("Relationship")
      .setCodes(
        " ", "No information provided",
        "0", "Resource",
        "1", "Version of resource",
        "2", "Related resource",
        "3", "Component part(s) of resource",
        "4", "Version of component part(s) of resource",
        "8", "No display constant generated"
      )
      .setMqTag("relationship")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect);

    setSubfieldsWithCardinality(
      "b", "Name of archiving agency", "NR",
      "c", "Name of Web archive or digital archive repository", "NR",
      "d", "Date range of archived material", "NR",
      "e", "Data provenance", "R",
      "f", "Archive completeness", "NR",
      "g", "Persistent identifier", "R",
      "h", "Non-functioning Uniform Resource Identifier", "R",
      "l", "Standardized information governing access", "R",
      "m", "Contact for access assistance", "R",
      "n", "Terms governing access", "R",
      "q", "Electronic format type", "R",
      "r", "Standardized information governing use and reproduction", "R",
      "s", "File size", "R",
      "t", "Terms governing use and reproduction", "R",
      "u", "Uniform Resource Identifier", "R",
      "x", "Nonpublic note", "R",
      "y", "Link text", "R",
      "z", "Public note", "R",
      "2", "Access method", "NR",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "7", "Access status", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("7").setCodes(
      "0", "Open access",
      "1", "Restricted access",
      "u", "Unspecified",
      "z", "Other"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("b")
      .setMqTag("archivingAgency");

    getSubfield("c")
      .setMqTag("webArchive");

    getSubfield("d")
      .setMqTag("dateRange");

    getSubfield("e")
      .setMqTag("dataProvenance");

    getSubfield("f")
      .setMqTag("completeness");

    getSubfield("g")
      .setMqTag("pid");

    getSubfield("h")
        .setMqTag("nonFunctioningURI");

    getSubfield("l")
      .setMqTag("access");

    getSubfield("m")
      .setMqTag("contact");

    getSubfield("n")
      .setMqTag("terms");

    getSubfield("q")
      .setMqTag("format");

    getSubfield("r")
      .setMqTag("use");

    getSubfield("s")
      .setMqTag("fileSize");

    getSubfield("t")
      .setMqTag("termsOfUse");

    getSubfield("u")
      .setMqTag("uri");

    getSubfield("x")
      .setMqTag("nonpublicNote");

    getSubfield("y")
      .setMqTag("linkText");

    getSubfield("z")
      .setMqTag("publicNote");

    getSubfield("2")
      .setMqTag("accessMethod")
      .setCodeList(ElectronicAccessMethodsCodeList.getInstance());

    getSubfield("3")
      .setMqTag("materialsSpecified");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies");

    getSubfield("6")
      .setBibframeTag("linkage");

    getSubfield("7")
      .setMqTag("accessStatus");

    getSubfield("8")
      .setMqTag("fieldLink");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
