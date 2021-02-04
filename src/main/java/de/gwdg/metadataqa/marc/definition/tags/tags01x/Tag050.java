package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Library of Congress Call Number
 * http://www.loc.gov/marc/bibliographic/bd050.html
 */
public class Tag050 extends DataFieldDefinition {

  private static Tag050 uniqueInstance;

  private Tag050() {
    initialize();
    postCreation();
  }

  public static Tag050 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag050();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "050";
    label = "Library of Congress Call Number";
    bibframeTag = "ClassificationLcc";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd050.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Existence in LC collection")
      .setCodes(
        " ", "No information provided",
        "0", "Item is in LC",
        "1", "Item is not in LC"
      )
      .setMqTag("existenceInLC")
      .setFrbrFunctions(DiscoverySelect, DiscoveryObtain);

    ind2 = new Indicator("Source of call number")
      .setCodes(
        "0", "Assigned by LC",
        "4", "Assigned by agency other than LC"
      )
      .setHistoricalCodes(
        " ", "No information provided",
        "0", "No series involved",
        "1", "Main series",
        "2", "Subseries",
        "3", "Sub-subseries"
      )
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Classification number", "R",
      "b", "Item number", "NR",
      "0", "Authority record control number or standard number", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("itemPortion")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("3")
      .setMqTag("materialsSpecified")
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

    setHistoricalSubfields(
      "d", "Supplementary class number (MU) [OBSOLETE, 1981]"
    );
  }
}
