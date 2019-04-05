package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Subject Added Entry - Personal Name
 * http://www.loc.gov/marc/bibliographic/bd600.html,
 * http://www.loc.gov/marc/bibliographic/bdx00.html
 */
public class Tag600 extends DataFieldDefinition {

  private static Tag600 uniqueInstance;

  private Tag600() {
    initialize();
    postCreation();
  }

  public static Tag600 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag600();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "600";
    label = "Subject Added Entry - Personal Name";
    mqTag = "PersonalNameSubject";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd600.html";

    ind1 = new Indicator("Type of personal name entry element")
      .setCodes(
        "0", "Forename",
        "1", "Surname",
        "3", "Family name"
      )
      .putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
        new Code("2", "Multiple surname")
      ))
      .setHistoricalCodes(
        "2", "Multiple surname [OBSOLETE, 1996]"
      )
      .setMqTag("type")
      .setFrbrFunctions(UseIdentify, ManagementProcess, ManagementSort);

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
      .setFrbrFunctions(UseIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Personal name", "NR",
      "b", "Numeration", "NR",
      "c", "Titles and other words associated with a name", "R",
      "d", "Dates associated with a name", "NR",
      "e", "Relator term", "R",
      "f", "Date of a work", "NR",
      "g", "Miscellaneous information", "R",
      "h", "Medium", "NR",
      "j", "Attribution qualifier", "R",
      "k", "Form subheading", "R",
      "l", "Language of a work", "NR",
      "m", "Medium of performance for music", "R",
      "n", "Number of part/section of a work", "R",
      "o", "Arranged statement for music", "NR",
      "p", "Name of part/section of a work", "R",
      "q", "Fuller form of name", "NR",
      "r", "Key for music", "NR",
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

    getSubfield("a").setMqTag("personalName")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("b").setMqTag("numeration")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("c").setMqTag("titlesAndWords")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("d").setMqTag("dates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("e").setMqTag("relatorTerm")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("f").setMqTag("dateOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("g").setMqTag("miscellaneous");
    getSubfield("h").setMqTag("medium")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("j").setMqTag("attributionQualifier")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("k").setMqTag("formSubheading")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("l").setMqTag("languageOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("m").setMqTag("mediumOfPerformance")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("n").setMqTag("numberOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("o").setMqTag("arrangedStatement")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("p").setMqTag("nameOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("q").setMqTag("fullerForm")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("r").setMqTag("keyForMusic")
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
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("4").setMqTag("relationship")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);

    putVersionSpecificSubfields(MarcVersion.FENNICA, Arrays.asList(
      new SubfieldDefinition("9", "Artikkeli", "NR")
    ));
  }

  public String getSource(DataField field) {
    if (field.getInd2().equals("7")) {
      return field.getSubfield("2").get(0).getValue();
    }
    return getInd2().getCode(field.getInd2()).getLabel();
  }
}
