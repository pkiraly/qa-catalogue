package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Negative Shelfmark
 */
public class Tag979 extends DataFieldDefinition {

  private static Tag979 uniqueInstance;

  private Tag979() {
    initialize();
    postCreation();
  }

  public static Tag979 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag979();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "979";
    label = "Negative Shelfmark";
    mqTag = "NegativeShelfmark";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    // subfields should be identical with 852,
    // @see Tag852
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
    getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());
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
      .setMqTag("sublocation");

    getSubfield("c")
      .setMqTag("shelvingLocation");

    getSubfield("d")
      .setMqTag("formerShelvingLocation");

    getSubfield("e")
      .setMqTag("address");

    getSubfield("f")
      .setMqTag("qualifier");

    getSubfield("g")
      .setMqTag("nonCodedQualifier");

    getSubfield("h")
      .setMqTag("classification");

    getSubfield("i")
      .setMqTag("item");

    getSubfield("j")
      .setMqTag("shelvingControlNumber");

    getSubfield("k")
      .setMqTag("prefix");

    getSubfield("l")
      .setMqTag("title");

    getSubfield("m")
      .setMqTag("suffix");

    getSubfield("n")
      .setMqTag("country");

    getSubfield("p")
      .setMqTag("pieceDesignation");

    getSubfield("q")
      .setMqTag("physicalCondition");

    getSubfield("s")
      .setMqTag("feeCode");

    getSubfield("t")
      .setMqTag("copyNumber");

    getSubfield("u")
      .setMqTag("uri");

    getSubfield("x")
      .setMqTag("nonpublicNote");

    getSubfield("z")
      .setMqTag("note");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materials");

    getSubfield("6");

    getSubfield("8")
      .setMqTag("fieldLink");

    sourceSpecificationType = SourceSpecificationType.Indicator1Is7AndSubfield2;
  }
}
