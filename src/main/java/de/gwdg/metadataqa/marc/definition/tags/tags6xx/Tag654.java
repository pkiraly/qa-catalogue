package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Subject Added Entry - Faceted Topical Terms
 * http://www.loc.gov/marc/bibliographic/bd654.html
 */
public class Tag654 extends DataFieldDefinition {

  private static Tag654 uniqueInstance;

  private Tag654() {
    initialize();
    postCreation();
  }

  public static Tag654 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag654();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "654";
    label = "Subject Added Entry - Faceted Topical Terms";
    mqTag = "FacetedTopicalTerms";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd654.html";

    ind1 = new Indicator("Level of subject")
      .setCodes(
        " ", "No information provided",
        "0", "No level specified",
        "1", "Primary",
        "2", "Secondary"
      )
      .setMqTag("subjectLevel");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Focus term", "R",
      "b", "Non-focus term", "R",
      "c", "Facet/hierarchy designation", "R",
      "e", "Relator term", "R",
      "v", "Form subdivision", "R",
      "y", "Chronological subdivision", "R",
      "z", "Geographic subdivision", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of heading or term", "NR",
      "3", "Materials specified", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("4").setCodeList(RelatorCodes.getInstance());
    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("focusTerm")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("A");

    getSubfield("b")
      .setMqTag("nonFocusTerm")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("facetOrHierarchy")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("relator")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("v")
      .setBibframeTag("formGenre").setMqTag("formSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("A");

    getSubfield("y")
      .setBibframeTag("temporal").setMqTag("chronologicalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("A");

    getSubfield("z")
      .setBibframeTag("geographic").setMqTag("geographicSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify)
      .setLevels("A");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setLevels("O");

    getSubfield("2")
      .setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("4")
      .setMqTag("relationship")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");

    fieldIndexer = SchemaFromSubfield2.getInstance();
    sourceSpecificationType = SourceSpecificationType.Subfield2;
  }
}
