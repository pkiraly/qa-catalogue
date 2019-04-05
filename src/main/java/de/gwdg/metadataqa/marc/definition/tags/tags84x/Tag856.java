package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ElectronicAccessMethodsCodeList;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

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
      "b", "Access number", "R",
      "c", "Compression information", "R",
      "d", "Path", "R",
      "f", "Electronic name", "R",
      "h", "Processor of request", "NR",
      "i", "Instruction", "R",
      "j", "Bits per second", "NR",
      "k", "Password", "NR",
      "l", "Logon", "NR",
      "m", "Contact for access assistance", "R",
      "n", "Name of location of host", "NR",
      "o", "Operating system", "NR",
      "p", "Port", "NR",
      "q", "Electronic format type", "NR",
      "r", "Settings", "NR",
      "s", "File size", "R",
      "t", "Terminal emulation", "R",
      "u", "Uniform Resource Identifier", "R",
      "v", "Hours access method available", "R",
      "w", "Record control number", "R",
      "x", "Nonpublic note", "R",
      "y", "Link text", "R",
      "z", "Public note", "R",
      "2", "Access method", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("w").setCodeList(OrganizationCodes.getInstance());
    getSubfield("2").setCodeList(ElectronicAccessMethodsCodeList.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("host")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("b").setMqTag("accessNumber")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("c").setMqTag("compression")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("d").setMqTag("path")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("f").setMqTag("name")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("h").setMqTag("processor");
    getSubfield("i").setMqTag("instruction")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("j").setMqTag("bitsPerSecond")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("k").setMqTag("password")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("l").setMqTag("logon")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("m").setMqTag("contact")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("n").setMqTag("location")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("o").setMqTag("operatingSystem")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("p").setMqTag("port")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("q").setMqTag("format")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("r").setMqTag("settings")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("s").setMqTag("fileSize");
    getSubfield("t").setMqTag("terminalEmulation")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("u").setMqTag("uri")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain);
    getSubfield("v").setMqTag("hours")
      .setFrbrFunctions(DiscoveryObtain);
    getSubfield("w").setMqTag("recordControlNumber");
    getSubfield("x").setMqTag("nonpublicNote");
    getSubfield("y").setMqTag("linkText");
    getSubfield("z").setMqTag("publicNote");
    getSubfield("2").setMqTag("accessMethod");
    getSubfield("3").setMqTag("materialsSpecified");
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);

    setHistoricalSubfields(
      "g", "Uniform Resource Name [OBSOLETE, 2000]"
    );
  }
}
