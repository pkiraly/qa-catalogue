package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd1OrIfEmptyFromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Government Document Classification Number
 * http://www.loc.gov/marc/bibliographic/bd086.html
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
    setLevels("A");

    ind1 = new Indicator("Number source")
      .setCodes(
        " ", "Source specified in subfield $2",
        "0", "Superintendent of Documents Classification System",
        "1", "Government of Canada Publications: Outline of Classification"
      )
      .setHistoricalCodes(
        "0", "United States",
        "1", "Canada",
        "2-9", "Reserved"
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
      "2", "Number source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setLevels("M");

    getSubfield("z")
      .setMqTag("canceled")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoveryObtain)
      .setLevels("A");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");

    fieldIndexer = SchemaFromInd1OrIfEmptyFromSubfield2.getInstance();
    sourceSpecificationType = SourceSpecificationType.Indicator1IsSpaceAndSubfield2;
  }
}
