package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Added Entry - Uncontrolled Related/Analytical Title
 * http://www.loc.gov/marc/bibliographic/bd740.html
 */
public class Tag740 extends DataFieldDefinition {

  private static Tag740 uniqueInstance;

  private Tag740() {
    initialize();
    postCreation();
  }

  public static Tag740 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag740();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "740";
    label = "Added Entry - Uncontrolled Related/Analytical Title";
    mqTag = "AddedUncontrolledRelatedOrAnalyticalTitle";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd740.html";

    ind1 = new Indicator("Nonfiling characters")
      .setCodes(
        "0", "No nonfiling characters",
        "1-9", "Number of nonfiling characters"
      )
      .setHistoricalCodes(
        " ", "Nonfiling characters not specified [OBSOLETE, 1980]"
      )
      .setMqTag("nonfilingCharacters")
      .setFrbrFunctions(ManagementProcess, ManagementSort);
    ind1.getCode("1-9").setRange(true);

    ind2 = new Indicator("Type of added entry")
      .setCodes(
        " ", "No information provided",
        "2", "Analytical entry"
      )
      .setHistoricalCodes(
        "0", "Alternative entry (BK, AM, CF, MP, MU) [OBSOLETE, 1993]",
        "1", "Secondary entry (BK, AM, CF, MP, MU) / Printed on card (VM) [OBSOLETE, 1993]",
        "3", "Not printed on card [OBSOLETE, 1993]"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Uncontrolled related/analytical title", "NR",
      "h", "Medium", "NR",
      "n", "Number of part/section of a work", "R",
      "p", "Name of part/section of a work", "R",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("M");

    getSubfield("h")
      .setMqTag("medium")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("O");

    getSubfield("n")
      .setBibframeTag("partNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("A");

    getSubfield("p")
      .setMqTag("nameOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("A");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
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
