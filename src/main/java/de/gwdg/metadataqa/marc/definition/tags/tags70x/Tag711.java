package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Added Entry - Meeting Name
 * http://www.loc.gov/marc/bibliographic/bd711.html
 */
public class Tag711 extends DataFieldDefinition {

  private static Tag711 uniqueInstance;

  private Tag711() {
    initialize();
    postCreation();
  }

  public static Tag711 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag711();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "711";
    label = "Added Entry - Meeting Name";
    mqTag = "AddedMeetingName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd711.html";

    ind1 = new Indicator("Type of meeting name entry element")
      .setCodes(
        "0", "Inverted name",
        "1", "Jurisdiction name",
        "2", "Name in direct order"
      )
      .setMqTag("meetingNameType")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementSort);
    ind2 = new Indicator("Type of added entry")
      .setCodes(
        " ", "No information provided",
        "2", "Analytical entry"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Meeting name or jurisdiction name as entry element", "NR",
      "c", "Location of meeting", "R",
      "d", "Date of meeting", "NR",
      "e", "Subordinate unit", "R",
      "f", "Date of a work", "NR",
      "g", "Miscellaneous information", "R",
      "h", "Medium", "NR",
      "i", "Relationship information", "R",
      "j", "Relator term", "R",
      "k", "Form subheading", "R",
      "l", "Language of a work", "NR",
      "n", "Number of part/section/meeting", "R",
      "p", "Name of part/section of a work", "R",
      "q", "Name of meeting following jurisdiction name entry element", "NR",
      "s", "Version", "NR",
      "t", "Title of a work", "NR",
      "u", "Affiliation", "NR",
      "x", "International Standard Serial Number", "NR",
      "0", "Authority record control number or standard number", "R",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("4").setCodeList(RelatorCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("c").setMqTag("locationOfMeeting")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("d").setMqTag("dates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("e").setMqTag("subordinateUnit")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("f").setMqTag("dateOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("g").setMqTag("miscellaneous");
    getSubfield("h").setMqTag("medium")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("i").setMqTag("relationship");
    getSubfield("j").setMqTag("relatorTerm");
    getSubfield("k").setMqTag("formSubheading")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("l").setMqTag("language")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("n").setMqTag("numberOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("p").setMqTag("nameOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("q").setMqTag("followingName")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("s").setMqTag("version")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("t").setMqTag("titleOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("u").setMqTag("affiliation")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("x").setMqTag("issn")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain);
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("4").setMqTag("relatorCode")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);
    getSubfield("6").setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    putVersionSpecificSubfields(MarcVersion.FENNICA, Arrays.asList(
      new SubfieldDefinition("9", "Artikkeli", "NR")
    ));
  }
}
