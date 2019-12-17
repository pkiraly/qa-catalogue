package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Date/Time and Place of an Event Note
 * http://www.loc.gov/marc/bibliographic/bd518.html
 */
public class Tag518 extends DataFieldDefinition {

  private static Tag518 uniqueInstance;

  private Tag518() {
    initialize();
    postCreation();
  }

  public static Tag518 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag518();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "518";
    label = "Date/Time and Place of an Event Note";
    bibframeTag = "Capture";
    mqTag = "DateTimeAndPlaceOfAnEventNote";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd518.html";
    setLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Date/time and place of an event note", "NR",
      "d", "Date of event", "R",
      "o", "Other event information", "R",
      "p", "Place of event", "R",
      "0", "Record control number", "R",
      "2", "Source of term", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
      .setLevels("M");

    getSubfield("d")
      .setBibframeTag("rdfs:label").setMqTag("date")
      .setLevels("A");

    getSubfield("o")
      .setBibframeTag("rdfs:label").setMqTag("otherInformation")
      .setLevels("O");

    getSubfield("p")
      .setBibframeTag("rdfs:label").setMqTag("place")
      .setLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setLevels("O");

    getSubfield("2")
      .setMqTag("source")
      .setLevels("A");

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
