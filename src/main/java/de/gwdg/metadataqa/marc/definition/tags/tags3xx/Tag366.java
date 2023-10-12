package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.AvailabilityStatusCodeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Trade Availability Information
 * http://www.loc.gov/marc/bibliographic/bd366.html
 */
public class Tag366 extends DataFieldDefinition {
  private static Tag366 uniqueInstance;

  private Tag366() {
    initialize();
    postCreation();
  }

  public static Tag366 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag366();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "366";
    label = "Trade Availability Information";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd366.html";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Publishers' compressed title identification", "NR",
      "b", "Detailed date of publication", "NR",
      "c", "Availability status code", "NR",
      "d", "Expected next availability date", "NR",
      "e", "Note", "NR",
      "f", "Publisher's discount category", "NR",
      "g", "Date made out of print", "NR",
      "j", "ISO country code", "NR",
      "k", "MARC country code", "NR",
      "m", "Identification of agency", "NR",
      "2", "Source of availability status code", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
    // TODO $b - regex: yyyymmdd
    // TODO $c - regex: yyyymmdd
    // TODO $d - regex: yyyymmdd
    // TODO $g - regex: yyyymmdd

    getSubfield("k").setCodeList(CountryCodes.getInstance());
    getSubfield("2").setCodeList(AvailabilityStatusCodeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("compressedTitle")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("detailedDateOfPublication")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("availability")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("nextAvailabilityDate")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("note")
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("discountCategory")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("dateMadeOutOfPrint")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("isoCountryCode")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("marcCountryCode")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("agency")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("2")
      .setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
