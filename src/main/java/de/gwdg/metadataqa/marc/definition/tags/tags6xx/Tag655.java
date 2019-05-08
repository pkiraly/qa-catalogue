package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.GenreFormCodeAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Index Term - Genre/Form
 * http://www.loc.gov/marc/bibliographic/bd655.html
 */
public class Tag655 extends DataFieldDefinition {

  private static Tag655 uniqueInstance;

  private Tag655() {
    initialize();
    postCreation();
  }

  public static Tag655 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag655();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "655";
    label = "Index Term - Genre/Form";
    bibframeTag = "GenreForm";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd655.html";

    ind1 = new Indicator("Type of heading")
      .setCodes(
        " ", "Basic",
        "0", "Faceted"
      )
      .setMqTag("type")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);

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
      "a", "Genre/form data or focus term", "NR",
      "b", "Non-focus term", "R",
      "c", "Facet/hierarchy designation", "R",
      "v", "Form subdivision", "R",
      "x", "General subdivision", "R",
      "y", "Chronological subdivision", "R",
      "z", "Geographic subdivision", "R",
      "0", "Authority record control number", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(GenreFormCodeAndTermSourceCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("b").setMqTag("nonfocusTerm")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("c").setMqTag("facet")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("v").setBibframeTag("genreForm").setMqTag("formSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("5").setMqTag("institutionToWhichFieldApplies")
      .setFrbrFunctions(ManagementProcess);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
