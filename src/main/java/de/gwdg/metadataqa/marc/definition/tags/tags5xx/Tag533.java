package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Reproduction Note
 * http://www.loc.gov/marc/bibliographic/bd533.html
 */
public class Tag533 extends DataFieldDefinition {

  private static Tag533 uniqueInstance;

  private Tag533() {
    initialize();
    postCreation();
  }

  public static Tag533 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag533();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "533";
    label = "Reproduction Note";
    mqTag = "Reproduction";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd533.html";
    setLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Type of reproduction", "NR",
      "b", "Place of reproduction", "R",
      "c", "Agency responsible for reproduction", "R",
      "d", "Date of reproduction", "NR",
      "e", "Physical description of reproduction", "NR",
      "f", "Series statement of reproduction", "R",
      "m", "Dates and/or sequential designation of issues reproduced", "R",
      "n", "Note about reproduction", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "7", "Fixed-length data elements of reproduction", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
    // TODO write parser for $7
    /*
     /0 - Type of date/Publication status
       /1-4 - Date 1
       /5-8 - Date 2
       /9-11 - Place of publication, production, or execution
       /12 - Frequency
       /13 - Regularity
       /14 - Form of item
     */

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("type")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("M", "M");

    getSubfield("b")
      .setMqTag("place")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setLevels("M", "M");

    getSubfield("c")
      .setMqTag("agency")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setLevels("A", "A");

    getSubfield("d")
      .setMqTag("date")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setLevels("A", "A");

    getSubfield("e")
      .setMqTag("physicalDescription")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setLevels("A", "A");

    getSubfield("f")
      .setMqTag("series")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setLevels("A", "A");

    getSubfield("m")
      .setMqTag("datesAndOrSequentialDesignation")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setLevels("A", "A");

    getSubfield("n")
      .setMqTag("note")
      .setLevels("O");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A", "A");

    /** TODO check FRBR functions for the subfields
     * 533$7/0:    [ManagementProcess]
     * 533$7/1-4:  [DiscoveryIdentify, DiscoverySelect, DiscoveryObtain]
     * 533$7/12:   [DiscoveryIdentify, DiscoverySelect, UseManage]
     * 533$7/13:   [DiscoveryIdentify, DiscoverySelect, UseManage]
     * 533$7/14:   [DiscoveryIdentify]
     * 533$7/5-8:  [DiscoveryIdentify, DiscoverySelect, DiscoveryObtain]
     * 533$7/9-11: [DiscoveryIdentify, DiscoverySelect, DiscoveryObtain]
     */
    getSubfield("7")
      .setMqTag("controlSubfield")
      .setLevels("O");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
