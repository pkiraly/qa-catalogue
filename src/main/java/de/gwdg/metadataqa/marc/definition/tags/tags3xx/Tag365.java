package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.PriceTypeCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Trade Price
 * http://www.loc.gov/marc/bibliographic/bd365.html
 */
public class Tag365 extends DataFieldDefinition {
  private static Tag365 uniqueInstance;

  private Tag365() {
    initialize();
    postCreation();
  }

  public static Tag365 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag365();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "365";
    label = "Trade Price";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd365.html";

    ind1 = new Indicator("Start/End designator")
      .setCodes(
        " ", "No information provided",
        "0", "Starting information",
        "1", "Ending information"
      );

    ind2 = new Indicator("State of issuance")
      .setCodes(
        " ", "Not specified",
        "0", "Closed",
        "1", "Open"
      );

    setSubfieldsWithCardinality(
      "a", "Price type code", "NR",
      "b", "Price amount", "NR",
      "c", "Currency code", "NR",
      "d", "Unit of pricing", "NR",
      "e", "Price note", "NR",
      "f", "Price effective from", "NR",
      "g", "Price effective until", "NR",
      "h", "Tax rate 1", "NR",
      "i", "Tax rate 2", "NR",
      "j", "ISO country code", "NR",
      "k", "MARC country code", "NR",
      "m", "Identification of pricing entity", "NR",
      "2", "Source of price type code", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("d").setCodes(
      "00", "Per copy of whole product [default value]",
      "01", "Per page for printed loose-leaf content only"
    );
    getSubfield("k").setCodeList(CountryCodes.getInstance());
    getSubfield("2").setCodeList(PriceTypeCodeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("priceTypeCode")
      .setFrbrFunctions(DiscoveryIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("b")
      .setMqTag("priceAmount")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("currencyCode")
      .setFrbrFunctions(DiscoveryIdentify, ManagementIdentify)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("unitOfPricing")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, UseInterpret, ManagementIdentify)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("priceNote")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("f")
      .setMqTag("priceEffectiveFrom")
      .setFrbrFunctions(DiscoverySearch, DiscoverySelect, ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("g")
      .setMqTag("priceEffectiveUntil")
      .setFrbrFunctions(DiscoverySearch, DiscoverySelect, ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("h")
      .setMqTag("taxRate1")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("i")
      .setMqTag("taxRate2")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("j")
      .setMqTag("isoCountryCode")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setLevels("A");

    getSubfield("k")
      .setMqTag("marcCountryCode")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setLevels("A");

    getSubfield("m")
      .setMqTag("identificationOfPricingEntity")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, UseInterpret)
      .setLevels("A");

    getSubfield("2")
      .setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify)
      .setLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
