package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;

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
    setCompilanceLevels("A", "A");

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
      "y", "Data provenance", "R",
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
      .setCompilanceLevels("M", "M");

    getSubfield("b")
      .setMqTag("place")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("M", "M");

    getSubfield("c")
      .setMqTag("agency")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("d")
      .setMqTag("date")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("e")
      .setMqTag("physicalDescription")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("f")
      .setMqTag("series")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain)
      .setCompilanceLevels("A", "A");

    getSubfield("m")
      .setMqTag("datesAndOrSequentialDesignation")
      .setFrbrFunctions(DiscoveryIdentify, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A", "A");

    getSubfield("n")
      .setMqTag("note")
      .setCompilanceLevels("O");

    getSubfield("y")
      .setMqTag("dataProvenance");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

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
      .setCompilanceLevels("O");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
