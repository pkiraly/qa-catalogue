package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Date/Time and Place of an Event
 * http://www.loc.gov/marc/bibliographic/bd033.html
 */
public class Tag033 extends DataFieldDefinition {

  private static Tag033 uniqueInstance;

  private Tag033() {
    initialize();
    postCreation();
  }

  public static Tag033 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag033();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "033";
    label = "Date/Time and Place of an Event";
    bibframeTag = "Capture";
    mqTag = "EventDateTimeAndPlace";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd033.html";

    ind1 = new Indicator("Type of date in subfield $a")
      .setCodes(
        " ", "No date information",
        "0", "Single date",
        "1", "Multiple single dates",
        "2", "Range of dates"
      )
      .setMqTag("dateType")
      .setFrbrFunctions(ManagementProcess);
    ind2 = new Indicator("Type of event")
      .setCodes(
        " ", "No information provided",
        "0", "Capture",
        "1", "Broadcast",
        "2", "Finding"
      )
      .setMqTag("eventType")
      .setFrbrFunctions(ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Formatted date/time", "R",
      "b", "Geographic classification area code", "R",
      "c", "Geographic classification subarea code", "R",
      "p", "Place of event", "R",
      "0", "Authority record control number", "R",
      "2", "Source of term", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
    getSubfield("0").setContentParser(RecordControlNumberParser.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("date")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("b")
      .setBibframeTag("place").setMqTag("area")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("subarea")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("A");

    getSubfield("p")
      .setBibframeTag("place")
      .setLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setLevels("O");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

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
