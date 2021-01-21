package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.DateSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Special Coded Dates
 * http://www.loc.gov/marc/bibliographic/bd046.html
 */
public class Tag046 extends DataFieldDefinition {

  private static Tag046 uniqueInstance;

  private Tag046() {
    initialize();
    postCreation();
  }

  public static Tag046 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag046();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "046";
    label = "Special Coded Dates";
    mqTag = "SpecialCodedDates";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd046.html";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Type of date code", "NR",
      "b", "Date 1, B.C.E. date", "NR",
      "c", "Date 1, C.E. date", "NR",
      "d", "Date 2, B.C.E. date", "NR",
      "e", "Date 2, C.E. date", "NR",
      "j", "Date resource modified", "NR",
      "k", "Beginning or single date created", "NR",
      "l", "Ending date created", "NR",
      "m", "Beginning of date valid", "NR",
      "n", "End of date valid", "NR",
      "o", "Single or starting date for aggregated content", "NR",
      "p", "Ending date for aggregated content", "NR",
      "2", "Source of date", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setCodes(
      "r", "Reissue and original date",
      "s", "Single date",
      "p", "Distribution and production dates",
      "t", "Publication and copyright dates",
      "x", "Incorrect dates",
      "q", "Questionable date",
      "n", "Unknown date",
      "i", "Inclusive dates",
      "k", "Bulk dates",
      "r", "Reissue and original dates",
      "m", "Multiple dates",
      "t", "Publication and copyright dates",
      "x", "Incorrect dates",
      "n", "Unknown date"
    );
    getSubfield("2").setCodeList(DateSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("bceDate1")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("bcDate1")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("bceDate2")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("ceDate2")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("modified")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("k")
      .setMqTag("beginningCreated")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("endingCreated")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("beginningOfDateValid")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("endOfDateValid")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseRestrict)
      .setCompilanceLevels("A");

    getSubfield("o")
      .setMqTag("startingDateForAggregated");

    getSubfield("p")
      .setMqTag("endingDateForAggregated");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");
  }
}
