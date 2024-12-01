package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Dewey Decimal Classification Number
 * https://www.loc.gov/marc/bibliographic/bd082.html
 */
public class Tag082 extends DataFieldDefinition {

  private static Tag082 uniqueInstance;

  private Tag082() {
    initialize();
    postCreation();
  }

  public static Tag082 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag082();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "082";
    label = "Dewey Decimal Classification Number";
    bibframeTag = "ClassificationDdc";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd082.html";
    setCompilanceLevels("O");

    ind1 = new Indicator("Type of edition")
      .setCodes(
        "0", "Full edition",
        "1", "Abridged edition",
        "7", "Other edition specified in subfield $2"
      )
      .setHistoricalCodes(
        " ", "No edition information recorded (BK, MU, VM, SE) [OBSOLETE]",
        "2", "Abridged NST version (BK, MU, VM, SE) [OBSOLETE]"
      )
      .setMqTag("editionType")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    ind2 = new Indicator("Source of classification number")
      .setCodes(
        " ", "No information provided",
        "0", "Assigned by LC",
        "4", "Assigned by agency other than LC"
      )
      .setHistoricalCodes(
        " ", "No information provided [OBSOLETE] [USMARC only, BK, CF, MU, VM, SE]"
      )
      .setMqTag("classificationSource")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Classification number", "R",
      "b", "Item number", "NR",
      "m", "Standard or optional designation", "NR",
      "q", "Assigning agency", "NR",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Edition information", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("q").setCodeList(OrganizationCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("classificationPortion").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("b")
      .setBibframeTag("itemPortion")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("m")
      .setMqTag("standard")
      .setCompilanceLevels("M");

    getSubfield("q")
      .setBibframeTag("source")
      .setCompilanceLevels("O");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setBibframeTag("edition")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("M");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));

    setHistoricalSubfields(
      "b", "DDC number-abridged NST version (SE) [OBSOLETE]"
    );
  }
}
