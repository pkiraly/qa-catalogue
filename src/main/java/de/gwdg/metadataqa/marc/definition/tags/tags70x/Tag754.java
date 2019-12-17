package de.gwdg.metadataqa.marc.definition.tags.tags70x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.TaxonomicClassificationSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Added Entry - Taxonomic Identification
 * http://www.loc.gov/marc/bibliographic/bd754.html
 */
public class Tag754 extends DataFieldDefinition {

  private static Tag754 uniqueInstance;

  private Tag754() {
    initialize();
    postCreation();
  }

  public static Tag754 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag754();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "754";
    label = "Added Entry - Taxonomic Identification";
    mqTag = "TaxonomicIdentification";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd754.html";
    setLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Taxonomic name", "R",
      "c", "Taxonomic category", "R",
      "d", "Common or alternative name", "R",
      "x", "Non-public note", "R",
      "z", "Public note", "R",
      "0", "Authority record control number", "R",
      "2", "Source of taxonomic identification", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(TaxonomicClassificationSourceCodes.getInstance());

    getSubfield("0").setContentParser(RecordControlNumberParser.getInstance());
    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("name")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("M");

    getSubfield("c")
      .setMqTag("category")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("O");

    getSubfield("d")
      .setMqTag("commonOrAlternativeName")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("O");

    getSubfield("x")
      .setMqTag("nonPublicNote")
      .setLevels("O");

    getSubfield("z")
      .setMqTag("publicNote")
      .setLevels("O");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setLevels("O");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("M");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");

    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
