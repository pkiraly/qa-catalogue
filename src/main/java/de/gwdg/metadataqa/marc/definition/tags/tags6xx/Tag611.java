package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd2AndSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Subject Added Entry - Meeting Name
 * http://www.loc.gov/marc/bibliographic/bd611.html,
 * http://www.loc.gov/marc/bibliographic/bdx10.html
 */
public class Tag611 extends DataFieldDefinition {

  private static Tag611 uniqueInstance;

  private Tag611() {
    initialize();
    postCreation();
  }

  public static Tag611 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag611();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "611";
    label = "Subject Added Entry - Meeting Name";
    mqTag = "SubjectAddedMeetingName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd611.html";

    ind1 = new Indicator("Type of meeting name entry element")
      .setCodes(
        "0", "Inverted name",
        "1", "Jurisdiction name",
        "2", "Name in direct order"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementSort);

    ind2 = new Indicator("Thesaurus")
      .setCodes(
        "0", "Library of Congress Subject Headings",
        "1", "LC subject headings for children's literature",
        "2", "Medical Subject Headings",
        "3", "National Agricultural Library subject authority file",
        "4", "Source not specified",
        "5", "Canadian Subject Headings",
        "6", "Répertoire de vedettes-matière",
        "7", "Source specified in subfield $2"
      )
      .setMqTag("thesaurus")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Meeting name or jurisdiction name as entry element", "NR",
      "c", "Location of meeting", "R",
      "d", "Date of meeting", "NR",
      "e", "Subordinate unit", "R",
      "f", "Date of a work", "NR",
      "g", "Miscellaneous information", "R",
      "h", "Medium", "NR",
      "j", "Relator term", "R",
      "k", "Form subheading", "R",
      "l", "Language of a work", "NR",
      "n", "Number of part/section/meeting", "R",
      "p", "Name of part/section of a work", "R",
      "q", "Name of meeting following jurisdiction name entry element", "NR",
      "s", "Version", "NR",
      "t", "Title of a work", "NR",
      "u", "Affiliation", "NR",
      "v", "Form subdivision", "R",
      "x", "General subdivision", "R",
      "y", "Chronological subdivision", "R",
      "z", "Geographic subdivision", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of heading or term", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
    getSubfield("4").setCodeList(RelatorCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

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
    getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("4").setMqTag("relatorCode")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("6").setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    fieldIndexer = SchemaFromInd2AndSubfield2.getInstance();

    putVersionSpecificSubfields(MarcVersion.FENNICA, Arrays.asList(
      new SubfieldDefinition("9", "Artikkeli", "NR")
    ));
  }
}
