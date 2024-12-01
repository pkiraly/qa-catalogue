package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySearch;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;

/**
 * Government Document Classification Number
 * https://www.loc.gov/marc/bibliographic/bd086.html
 */
public class Tag086 extends DataFieldDefinition {

  private static Tag086 uniqueInstance;

  private Tag086() {
    initialize();
    postCreation();
  }

  public static Tag086 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag086();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "086";
    label = "Government Document Classification Number";
    bibframeTag = "Classification";
    mqTag = "GovernmentDocumentClassification";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd086.html";
    setCompilanceLevels("A");

    ind1 = new Indicator("Number source")
      .setCodes(
        " ", "Source specified in subfield $2",
        "0", "Superintendent of Documents Classification System",
        "1", "Government of Canada Publications: Outline of Classification"
      )
      .setHistoricalCodes(
        "0", "United States",
        "1", "Canada",
        "2", "Reserved",
        "3", "Reserved",
        "4", "Reserved",
        "5", "Reserved",
        "6", "Reserved",
        "7", "Reserved",
        "8", "Reserved",
        "9", "Reserved"
      )
      .setMqTag("numberSource")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    ind2 = new Indicator()
      .setHistoricalCodes(
        " ", "when first indicator is not \"1\"",
        "0", "IC cat. no.",
        "1", "Cat. IC, no.",
        "2", "QP cat. no.",
        "3", "Cat. IR, no.",
        "4", "DSS cat. no.",
        "5", "Cat. MAS, no."
      );

    setSubfieldsWithCardinality(
      "a", "Classification number", "NR",
      "z", "Canceled/invalid classification number", "R",
      "0", "Authority record control number or standard number", "R",
      "1", "Real World Object URI", "R",
      "2", "Number source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("M");

    getSubfield("z")
      .setMqTag("canceled")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setCompilanceLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    sourceSpecificationType = SourceSpecificationType.Indicator1IsSpaceAndSubfield2;

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
