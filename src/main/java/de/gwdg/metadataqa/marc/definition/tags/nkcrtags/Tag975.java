package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

public class Tag975 extends DataFieldDefinition {

  private static Tag975 uniqueInstance;

  private Tag975() {
    initialize();
    postCreation();
  }

  public static Tag975 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag975();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "975";
    label = "Added Entry - Personal Name";
    mqTag = "AddedPersonalName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator("Type of personal name entry element")
      .setCodes(
        "0", "Forename",
        "1", "Surname",
        "3", "Family name"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess, ManagementSort);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Personal name", "NR",
      "b", "Numeration", "NR",
      "c", "Titles and words associated with a name", "R",
      "d", "Dates associated with a name", "NR",
      "e", "Relator term", "R",
      "f", "Date of a work", "NR",
      "g", "Miscellaneous information", "R",
      "j", "Attribution qualifier", "R",
      "k", "Form subheading", "R",
      "l", "Language of a work", "NR",
      "n", "Number of part/section of a work", "R",
      "p", "Name of part/section of a work", "R",
      "q", "Fuller form of name", "NR",
      "t", "Title of a work", "NR",
      "u", "Affiliation", "NR",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of heading or term", "NR",
      "4", "Relator code", "R",
      "6", "Linkage", "NR",
      "7", "NKCR Authority ID", "NR",
      "8", "Field link and sequence number", "R",
      "9", "NKCR Authority field - tracing form", "NR"
    );

    getSubfield("2").setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());
    getSubfield("4").setCodeList(RelatorCodes.getInstance());

    getSubfield("0").setContentParser(RecordControlNumberParser.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("personalName")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M", "M");

    getSubfield("b")
      .setMqTag("numeration")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("c")
      .setMqTag("titlesAndWords")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("d")
      .setMqTag("dates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

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

    getSubfield("j")
      .setMqTag("attributionQualifier")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("k")
      .setMqTag("formSubheading")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("l")
      .setMqTag("language")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("n")
      .setMqTag("numberOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("p")
      .setMqTag("nameOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("q")
      .setMqTag("fullerForm")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("t")
      .setMqTag("titleOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A");

    getSubfield("u")
      .setMqTag("affiliation")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setCompilanceLevels("O");

    getSubfield("2")
      .setMqTag("source");

    getSubfield("4")
      .setMqTag("relatorCode")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O", "A");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
