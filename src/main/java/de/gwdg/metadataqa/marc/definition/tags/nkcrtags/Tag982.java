package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Added Entry - Corporate Name
 * http://www.loc.gov/marc/bibliographic/bd710.html
 */
public class Tag982 extends DataFieldDefinition {

  private static Tag982 uniqueInstance;

  private Tag982() {
    initialize();
    postCreation();
  }

  public static Tag982 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag982();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "982";
    label = "Added Entry - Corporate Name";
    mqTag = "AddedCorporateName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator("Type of corporate name entry element")
      .setCodes(
        "0", "Inverted name",
        "1", "Jurisdiction name",
        "2", "Name in direct order"
      )
      .setMqTag("nameType")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementSort);
    ind2 = new Indicator("Type of added entry")
      .setCodes(
        " ", "No information provided",
        "2", "Analytical entry"
      )
      .setMqTag("entryType")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Corporate name or jurisdiction name as entry element", "NR",
      "b", "Subordinate unit", "R",
      "c", "Location of meeting", "R",
      "d", "Date of meeting or treaty signing", "R",
      "e", "Relator term", "R",
      "f", "Date of a work", "NR",
      "g", "Miscellaneous information", "R",
      "h", "Medium", "NR",
      "i", "Relationship information", "R",
      "k", "Form subheading", "R",
      "l", "Language of a work", "NR",
      "m", "Medium of performance for music", "R",
      "n", "Number of part/section/meeting", "R",
      "o", "Arranged statement for music", "NR",
      "p", "Name of part/section of a work", "R",
      "r", "Key for music", "NR",
      "s", "Version", "NR",
      "t", "Title of a work", "NR",
      "u", "Affiliation", "NR",
      "x", "International Standard Serial Number", "NR",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of heading or term", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "5", "Kód instituce:signatura exempláře", "NR",
      "6", "Linkage", "NR",
      "7", "NKCR Authority ID", "NR",
      "8", "(Místo) a datum získání knihy (z exlibris, supralibros apod.)", "R",
      "9", "Slovní vyjádření kódu role", "NR"
    );

    getSubfield("2").setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());
    getSubfield("4").setCodeList(RelatorCodes.getInstance());

    getSubfield("0").setContentParser(RecordControlNumberParser.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setMqTag("subordinateUnit")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("locationOfMeeting")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("dates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("relatorTerm")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("f")
      .setMqTag("dateOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("miscellaneous")
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("medium")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("i")
      .setMqTag("relationship")
      .setCompilanceLevels("O");

    getSubfield("k")
      .setMqTag("formSubheading")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("language")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("mediumOfPerformance")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("numberOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("o")
      .setMqTag("arrangedStatement")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("p")
      .setMqTag("nameOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("r")
      .setMqTag("keyForMusic")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("s")
      .setMqTag("version")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("t")
      .setMqTag("titleOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("affiliation")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("x")
      .setMqTag("issn")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("O");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setCompilanceLevels("O");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("4")
      .setMqTag("relatorCode")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("5")
      .setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
