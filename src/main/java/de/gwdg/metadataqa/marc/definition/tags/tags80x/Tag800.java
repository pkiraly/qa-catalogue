package de.gwdg.metadataqa.marc.definition.tags.tags80x;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Series Added Entry - Personal Name
 * http://www.loc.gov/marc/bibliographic/bd800.html
 */
public class Tag800 extends DataFieldDefinition {

  private static Tag800 uniqueInstance;

  private Tag800() {
    initialize();
    postCreation();
  }

  public static Tag800 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag800();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "800";
    label = "Series Added Entry - Personal Name";
    mqTag = "SeriesAddedPersonalName";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd800.html";

    ind1 = new Indicator("Type of personal name entry element")
      .setCodes(
        "0", "Forename",
        "1", "Surname",
        "3", "Family name"
      )
      .putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
        new Code("2", "Multiple surname")
      ))
      .setMqTag("type")
      .setFrbrFunctions(UseIdentify, ManagementProcess, ManagementSort);
    ind2 = new Indicator();

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
      "v", "Volume/sequential designation", "NR",
      "w", "Bibliographic record control number", "R",
      "x", "International Standard Serial Number", "NR",
      "0", "Authority record control number or standard number", "R",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "5", "Institution to which field applies", "R",
      "6", "Linkage", "NR",
      "7", "Control subfield", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("4").setCodeList(RelatorCodes.getInstance());
    getSubfield("7").setPositions(Arrays.asList(
      new ControlSubfieldDefinition("Type of record", 0, 1)
        .setCodes(Utils.generateCodes(
          "a", "Language material",
          "c", "Notated music",
          "d", "Manuscript notated music",
          "e", "Cartographic material",
          "f", "Manuscript cartographic material",
          "g", "Projected medium",
          "i", "Nonmusical sound recording",
          "j", "Musical sound recording",
          "k", "Two-dimensional nonprojectable graphic",
          "m", "Computer file",
          "o", "Kit",
          "p", "Mixed material",
          "r", "Three-dimensional artifact or naturally occurring object",
          "t", "Manuscript language material"
        )),
      new ControlSubfieldDefinition("Bibliographic level", 1, 2)
        .setCodes(Utils.generateCodes(
          "a", "Monographic component part",
          "b", "Serial component part",
          "c", "Collection",
          "d", "Subunit",
          "i", "Integrating resource",
          "m", "Monograph/item",
          "s", "Serial"
        ))
    ));

    getSubfield("6").setContentParser(LinkageParser.getInstance());
    getSubfield("x").setValidator(ISSNValidator.getInstance());

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
    getSubfield("l").setMqTag("language")
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
    getSubfield("v").setMqTag("volumeDesignation")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("w").setMqTag("bibliographicRecordControlNumber");
    getSubfield("x").setMqTag("issn");
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("3").setMqTag("materialsSpecified");
    getSubfield("4").setMqTag("relatorCode")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies");
    getSubfield("6").setMqTag("linkage")
      .setFrbrFunctions(UseIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(UseIdentify, ManagementProcess);

    putVersionSpecificSubfields(MarcVersion.DNB, Arrays.asList(
      new SubfieldDefinition("9", "Sortierzählung", "R")
    ));
    putVersionSpecificSubfields(MarcVersion.FENNICA, Arrays.asList(
      new SubfieldDefinition("9", "Artikkeli", "NR")
    ));
  }
}
