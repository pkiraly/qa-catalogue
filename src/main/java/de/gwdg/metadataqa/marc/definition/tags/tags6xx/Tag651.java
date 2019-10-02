package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd2AndSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Subject Added Entry - Geographic Name
 * http://www.loc.gov/marc/bibliographic/bd651.html
 */
public class Tag651 extends DataFieldDefinition {

  private static Tag651 uniqueInstance;

  private Tag651() {
    initialize();
    postCreation();
  }

  public static Tag651 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag651();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "651";
    label = "Subject Added Entry - Geographic Name";
    bibframeTag = "Geographic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd651.html";

    ind1 = new Indicator();

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
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    setSubfieldsWithCardinality(
      "a", "Geographic name", "NR",
      "e", "Relator term", "R",
      "g", "Miscellaneous information", "R",
      "4", "Relationship", "R",
      "v", "Form subdivision", "R",
      "x", "General subdivision", "R",
      "y", "Chronological subdivision", "R",
      "z", "Geographic subdivision", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of heading or term", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
    getSubfield("4").setCodeList(RelatorCodes.getInstance());
    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("e").setMqTag("relator")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("g").setMqTag("miscellaneous");
    getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setBibframeTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("4").setMqTag("relationship")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

    fieldIndexer = SchemaFromInd2AndSubfield2.getInstance();

    setHistoricalSubfields(
      "b", "Geographic name following place entry element [OBSOLETE, 1981]"
    );
  }
}
