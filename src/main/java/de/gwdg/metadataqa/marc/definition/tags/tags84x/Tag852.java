package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Location
 * http://www.loc.gov/marc/bibliographic/bd852.html
 */
public class Tag852 extends DataFieldDefinition {

  private static Tag852 uniqueInstance;

  private Tag852() {
    initialize();
    postCreation();
  }

  public static Tag852 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag852();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "852";
    label = "Location";
    mqTag = "Location";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd852.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Shelving scheme")
      .setCodes(
        " ", "No information provided",
        "0", "Library of Congress classification",
        "1", "Dewey Decimal classification",
        "2", "National Library of Medicine classification",
        "3", "Superintendent of Documents classification",
        "4", "Shelving control number",
        "5", "Title",
        "6", "Shelved separately",
        "7", "Source specified in subfield $2",
        "8", "Other scheme"
      )
      .setMqTag("shelvingScheme")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    ind2 = new Indicator("Shelving order")
      .setCodes(
        " ", "No information provided",
        "0", "Not enumeration",
        "1", "Primary enumeration",
        "2", "Alternative enumeration"
      )
      .setMqTag("shelvingOrder");

    setSubfieldsWithCardinality(
      "a", "Location", "NR",
      "b", "Sublocation or collection", "R",
      "c", "Shelving location", "R",
      "d", "Former shelving location", "R",
      "e", "Address", "R",
      "f", "Coded location qualifier", "R",
      "g", "Non-coded location qualifier", "R",
      "h", "Classification part", "NR",
      "i", "Item part", "R",
      "j", "Shelving control number", "NR",
      "k", "Call number prefix", "R",
      "l", "Shelving form of title", "NR",
      "m", "Call number suffix", "R",
      "n", "Country code", "NR",
      "p", "Piece designation", "NR",
      "q", "Piece physical condition", "NR",
      "s", "Copyright article-fee code", "R",
      "t", "Copy number", "NR",
      "u", "Uniform Resource Identifier", "R",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R",
      "2", "Source of classification or shelving scheme", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setCodeList(OrganizationCodes.getInstance());
    getSubfield("n").setCodeList(CountryCodes.getInstance());
    getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance())
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("f").setCodes(
      "l", "Latest",
      "p", "Previous",
      " ", "Number of units: No information provided",
      "1-9", "Number of units",
      "m", "Month(s) time",
      "w", "Week(s) time",
      "y", "Year(s) time",
      "e", "Edition(s) part",
      "i", "Issue(s) part",
      "s", "Supplement(s) part"
    ).getCode("1-9").setRange(true);

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("location")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("M")
      .setLocalCodes(MarcVersion.BL,
        "ABP", "Aberdeen City Libraries",
        "BODBL", "Bodleian Library",
        "BRG", "Brighton Central Library",
        "stpancras", "British Library",
        "CUL", "Cambridge University Library",
        "CUM", "Cumbernauld News & Kilsyth Chronicle",
        "DBA", "Dumbarton District Libraries",
        "bryson", "Durham University",
        "HAD", "East Lothian District Libraries, Haddington",
        "HAM", "Hamilton District Libraries (Hamilton Town House Library)",
        "INV", "Highland Regional Library Service, Inverness",
        "HUDu", "Huddersfield University Library",
        "GRE", "Inverclyde District Libraries, Greenock",
        "LAR", "Largs and District Historical Society",
        "LOUp", "Loughton Central Library (National Jazz Archive)",
        "Gp", "Mitchell Library, Glasgow",
        "LV", "National Art Library (Victoria and Albert Museum)",
        "NLS", "National Library of Scotland",
        "NLW", "National Library of Wales",
        "KIK", "Orkney Islands Library, Kirkwall",
        "PER", "Perth and Kinross District Libraries (AK Bell Library, Perth)",
        "Bfr", "Public Record Office for Northern Ireland",
        "main", "Register of Preservation Surrogates",
        "RSL", "Royal Society, London",
        "Lrvc", "Royal Veterinary College, London",
        "Shp", "Sheffield City Library",
        "LERW", "Shetland Library, Lerwick",
        "STIcrs", "Stirling Central Region School Library Service (Stirling Library Headquarters)",
        "tcl", "Trinity College Dublin",
        "BT", "Tweedale Press Group, Berwick-upon-Tweed",
        "Bffm", "Ulster Folk and Transport Museum",
        "YMI", "York Minister Library"
      )
    ;

    getSubfield("b")
      .setMqTag("sublocation")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("shelvingLocation")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("formerShelvingLocation")
      .setCompilanceLevels("O");

    getSubfield("e")
      .setMqTag("address")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("O");

    getSubfield("f")
      .setMqTag("qualifier")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("O");

    getSubfield("g")
      .setMqTag("nonCodedQualifier")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("O");

    getSubfield("h")
      .setMqTag("classification")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("item")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("shelvingControlNumber")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("prefix")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("title")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("suffix")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("country")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("O");

    getSubfield("p")
      .setMqTag("pieceDesignation")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("q")
      .setMqTag("physicalCondition")
      .setFrbrFunctions(DiscoverySelect, UseManage)
      .setCompilanceLevels("O");

    getSubfield("s")
      .setMqTag("feeCode")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("O");

    getSubfield("t")
      .setMqTag("copyNumber")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setCompilanceLevels("A");

    getSubfield("x")
      .setMqTag("nonpublicNote")
      .setCompilanceLevels("O");

    getSubfield("z")
      .setMqTag("note")
      .setCompilanceLevels("O");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("3")
      .setMqTag("materials")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    sourceSpecificationType = SourceSpecificationType.Indicator1Is7AndSubfield2;
  }
}
