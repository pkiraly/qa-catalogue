package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ElectronicAccessMethodsCodeList;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

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
    label = "Electronic Location and Access (OGYK)";
    mqTag = "ElectronicLocationAndAccessOGYK";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:856";
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
      "r", "Standardized information governing use and reproduction", "NR",
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
      "7", "Access status", "NR",
      "8", "Field link and sequence number", "R",
      "4", "Aleph subfield related to copyright", "NR",
      "9", "Aleph/ANL fulltext linkage", "NR"
    );

    getSubfield("4").setCodes(
      "N", "ne legyen copyright üzenet"
    );

    getSubfield("x").setCodes(
      "fulltext", "teljes szövegű tartalom",
      "nofulltext", "nincs teljes szövegű tartalom"
    );

    getSubfield("7").setCodes(
      "0", "Open access",
      "1", "Restricted access",
      "u", "Unspecified",
      "z", "Other"
    );

    getSubfield("w").setCodeList(OrganizationCodes.getInstance());
    getSubfield("2").setCodeList(ElectronicAccessMethodsCodeList.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("host")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("accessNumber")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

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

    getSubfield("m")
      .setMqTag("contact")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("n")
      .setMqTag("location")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

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

    getSubfield("r")
      .setMqTag("useAndReproductionRights");

    getSubfield("s")
      .setMqTag("fileSize")
      .setCompilanceLevels("O");

    getSubfield("t")
      .setMqTag("terminalEmulation")
      .setFrbrFunctions(DiscoveryObtain)
      .setCompilanceLevels("A");

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

    setHistoricalSubfields(
      "g", "Uniform Resource Name [OBSOLETE, 2000]"
      // "r", "Settings [OBSOLETE, 2020]" -- but it was reintroduced with another definition in 2022
    );
  }
}
