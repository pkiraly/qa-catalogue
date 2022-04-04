package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.ElectronicAccessMethodsCodeList;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Electronic Location and Access
 * http://www.loc.gov/marc/bibliographic/bd856.html
 */
public class Tag856 extends DataFieldDefinition {

  private static Tag856 uniqueInstance;

  private Tag856() {
    initialize();
    postCreation();
  }

  public static Tag856 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag856();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "856";
    label = "Electronic Location and Access";
    mqTag = "ElectronicLocationAndAccess";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd856.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Access method")
      .setCodes(
        " ", "No information provided",
        "0", "Email",
        "1", "FTP",
        "2", "Remote login (Telnet)",
        "3", "Dial-up",
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
        "8", "No display constant generated"
      )
      .setMqTag("relationship")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect);

    setSubfieldsWithCardinality(
      "a", "Host name", "R",
      "c", "Compression information", "R",
      "d", "Path", "R",
      "f", "Electronic name", "R",
      "m", "Contact for access assistance", "R",
      "o", "Operating system", "NR",
      "p", "Port", "NR",
      "q", "Electronic format type", "NR",
      "s", "File size", "R",
      "u", "Uniform Resource Identifier", "R",
      "v", "Hours access method available", "R",
      "w", "Record control number", "R",
      "x", "Nonpublic note", "R",
      "y", "Link text", "R",
      "z", "Public note", "R",
      "2", "Access method", "NR",
      "3", "Materials specified", "NR",
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

    getSubfield("w").setCodeList(OrganizationCodes.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("host")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    /*
    getSubfield("b")
      .setMqTag("accessNumber")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");
    */

    getSubfield("c")
      .setMqTag("compression")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("path")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("name")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    /*
    getSubfield("h")
      .setMqTag("processor")
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("instruction")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("j")
      .setMqTag("bitsPerSecond")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("password")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("logon")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");
   */

    getSubfield("m")
      .setMqTag("contact")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    /*
    getSubfield("n")
      .setMqTag("location")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");
    */

    getSubfield("o")
      .setMqTag("operatingSystem")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("p")
      .setMqTag("port")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("q")
      .setMqTag("format")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    /*
    getSubfield("r")
      .setMqTag("settings")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");
     */

    getSubfield("s")
      .setMqTag("fileSize")
      .setCompilanceLevels("O");

    /*
    getSubfield("t")
      .setMqTag("terminalEmulation")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");
     */

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("v")
      .setMqTag("hours")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("w")
      .setMqTag("recordControlNumber")
      .setCompilanceLevels("O");

    getSubfield("x")
      .setMqTag("nonpublicNote")
      .setCompilanceLevels("O");

    getSubfield("y")
      .setMqTag("linkText")
      .setCompilanceLevels("O");

    getSubfield("z")
      .setMqTag("publicNote")
      .setCompilanceLevels("O");

    getSubfield("2")
      .setMqTag("accessMethod")
      .setCodeList(ElectronicAccessMethodsCodeList.getInstance())
      .setCompilanceLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("accessStatus");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("4", "Aleph subfield related to copyright", "NR"),
      new SubfieldDefinition("9", "Aleph/ANL fulltext linkage", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    setHistoricalSubfields(
      "g", "Uniform Resource Name [OBSOLETE, 2000]",
      "b", "Access number [OBSOLETE, 2020]", // "R",
      "h", "Processor of request [OBSOLETE, 2020]", // "NR",
      "i", "Instruction [OBSOLETE, 2020]", // "R",
      "j", "Bits per second [OBSOLETE, 2020]", // "NR",
      "k", "Password [OBSOLETE, 2020]", // "NR",
      "l", "Logon [OBSOLETE, 2020]", // "NR",
      "n", "Name of location of host [OBSOLETE, 2020]", // "NR",
      "r", "Settings [OBSOLETE, 2020]", // "NR",
      "t", "Terminal emulation [OBSOLETE, 2020]"//, "R",
    );
  }
}
