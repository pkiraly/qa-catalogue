package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Collective Uniform Title
 * http://www.loc.gov/marc/bibliographic/bd243.html
 */
public class Tag243 extends DataFieldDefinition {
  private static Tag243 uniqueInstance;

  private Tag243() {
    initialize();
    postCreation();
  }

  public static Tag243 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag243();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "243";
    label = "Collective Uniform Title";
    bibframeTag = "CollectiveTitle";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd243.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Uniform title printed or displayed")
      .setCodes(
        "0", "Not printed or displayed",
        "1", "Printed or displayed"
      )
      .setHistoricalCodes(
        "2", "Not printed on card, title added entry (MU) [USMARC only] [OBSOLETE, 1993] / Selections (extracts) [CAN/MARC only] [OBSOLETE]",
        "3", "Printed on card, title added entry (MU) [USMARC only] [OBSOLETE, 1993] / Other collective titles [CAN/MARC only] [OBSOLETE]"
      )
      .setMqTag("printedOrDisplayed")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator("Nonfiling characters")
      .setCodes(
        "0-9", "Number of nonfiling characters"
      )
      .setMqTag("nonfilingCharacters")
      .setFrbrFunctions(ManagementProcess, ManagementSort);
    ind2.getCode("0-9").setRange(true);

    setSubfieldsWithCardinality(
      "a", "Uniform title", "NR",
      "d", "Date of treaty signing", "R",
      "f", "Date of a work", "NR",
      "g", "Miscellaneous information", "R",
      "h", "Medium", "NR",
      "k", "Form subheading", "R",
      "l", "Language of a work", "NR",
      "m", "Medium of performance for music", "R",
      "n", "Number of part/section of a work", "R",
      "o", "Arranged statement for music", "NR",
      "p", "Name of part/section of a work", "R",
      "r", "Key for music", "NR",
      "s", "Version", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("mainTitle")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("d")
      .setMqTag("dateOfTreaty")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("dateOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("miscellaneous")
      .setCompilanceLevels("O");

    getSubfield("h")
      .setMqTag("medium")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("k")
      .setMqTag("formSubheading")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("languageOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("mediumOfPerformance")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setBibframeTag("partNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("o")
      .setMqTag("arrangedStatement")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("p")
      .setMqTag("nameOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("r")
      .setMqTag("keyForMusic")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("s")
      .setBibframeTag("version")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
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
