package de.gwdg.metadataqa.marc.definition.tags.tags20x;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.codelist.NameAndTitleAuthoritySourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementDisplay;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementSort;

/**
 * Uniform Title
 * http://www.loc.gov/marc/bibliographic/bd240.html
 */
public class Tag240 extends DataFieldDefinition {
  private static Tag240 uniqueInstance;

  private Tag240() {
    initialize();
    postCreation();
  }

  public static Tag240 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag240();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "240";
    label = "Uniform Title";
    mqTag = "UniformTitle";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd240.html";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Uniform title printed or displayed")
      .setCodes(
        "0", "Not printed or displayed",
        "1", "Printed or displayed"
      )
      .putVersionSpecificCodes(MarcVersion.SZTE, Arrays.asList(
        new EncodedValue(" ", "Not specified")
      ))
      .setHistoricalCodes(
        "2", "Not printed on card, title added entry (MU) [OBSOLETE, 1993]",
        "3", "Printed on card, title added entry (MU) [OBSOLETE, 1993]"
      )
      .setMqTag("printedOrDisplayed")
      .setFrbrFunctions(ManagementProcess, ManagementDisplay);

    ind2 = new Indicator("Nonfiling characters")
      .setCodes(
        "0-9", "Number of nonfiling characters"
      )
      .setMqTag("nonfilingCharacters")
      .setFrbrFunctions(ManagementProcess, ManagementSort);
    ind2.getCode("0-9").setRange(true);

    setSubfieldsWithCardinality(
      "a", "Uniform title", "NR",
      "d", "Date of treaty signing", "R",
      "f", "Date of a work", "NR",
      "g", "Miscellaneous information", "R",
      "h", "Medium", "NR",
      "k", "Form subheading", "R",
      "l", "Language of a work", "NR",
      "m", "Medium of performance for music", "R",
      "n", "Number of part/section of a work", "R",
      "o", "Arranged statement for music", "NR",
      "p", "Name of part/section of a work", "R",
      "r", "Key for music", "NR",
      "s", "Version", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Source of heading or term", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("mainTitle")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("M", "M");

    getSubfield("d")
      .setMqTag("dateOfTreaty")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("f")
      .setMqTag("dateOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("g")
      .setMqTag("miscellaneous")
      .setCompilanceLevels("A", "A");

    getSubfield("h")
      .setMqTag("medium")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("O");

    getSubfield("k")
      .setMqTag("formSubheading")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A", "A");

    getSubfield("l")
      .setMqTag("languageOfAWork")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A", "A");

    getSubfield("m")
      .setMqTag("mediumOfPerformance")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A", "A");

    getSubfield("n")
      .setBibframeTag("partNumber")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("o")
      .setMqTag("arrangedStatement")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("p")
      .setMqTag("nameOfPart")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("r")
      .setMqTag("keyForMusic")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setCompilanceLevels("A", "A");

    getSubfield("s")
      .setBibframeTag("version")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("A", "A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance())
      .setCompilanceLevels("O");

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setCodeList(NameAndTitleAuthoritySourceCodes.getInstance());

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
