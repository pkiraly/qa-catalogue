package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd1OrIf7FromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Geographic Classification
 * http://www.loc.gov/marc/bibliographic/bd052.html
 */
public class Tag052 extends DataFieldDefinition {

  private static Tag052 uniqueInstance;

  private Tag052() {
    initialize();
    postCreation();
  }

  public static Tag052 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag052();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "052";
    label = "Geographic Classification";
    bibframeTag = "Place";
    mqTag = "GeographicClassification";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd052.html";

    ind1 = new Indicator("Code source")
      .setCodes(
        " ", "Library of Congress Classification",
        "1", "U.S. Dept. of Defense Classification",
        "7", "Source specified in subfield $2"
      )
      .setHistoricalCodes(
        "0", "U.S. Dept. of Defense Classification [OBSOLETE, 2002]"
      )
      .setMqTag("codeSource")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Geographic classification area code", "NR",
      "b", "Geographic classification subarea code", "R",
      "d", "Populated place name", "R",
      "2", "Code source", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(ClassificationSchemeSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoverySelect);
    getSubfield("b").setMqTag("subarea")
      .setFrbrFunctions(DiscoverySearch, DiscoverySelect);
    getSubfield("d").setBibframeTag("rdfs:label")
      .setFrbrFunctions(DiscoverySearch, DiscoverySelect);
    getSubfield("2").setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    fieldIndexer = SchemaFromInd1OrIf7FromSubfield2.getInstance();

    setHistoricalSubfields(
      "c", "Subject (MP) [OBSOLETE]"
    );
  }
}
