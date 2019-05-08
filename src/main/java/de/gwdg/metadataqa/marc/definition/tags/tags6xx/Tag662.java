package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Subject Added Entry - Hierarchical Place Name
 * http://www.loc.gov/marc/bibliographic/bd662.html
 */
public class Tag662 extends DataFieldDefinition {

  private static Tag662 uniqueInstance;

  private Tag662() {
    initialize();
    postCreation();
  }

  public static Tag662 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag662();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "662";
    label = "Subject Added Entry - Hierarchical Place Name";
    bibframeTag = "HierarchicalGeographic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd662.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Country or larger entity", "R",
      "b", "First-order political jurisdiction", "NR",
      "c", "Intermediate political jurisdiction", "R",
      "d", "City", "NR",
      "e", "Relator term", "R",
      "f", "City subsection", "R",
      "g", "Other nonjurisdictional geographic region and feature", "R",
      "h", "Extraterrestrial area", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source of heading or term", "NR",
      "4", "Relationship", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
    getSubfield("4").setCodeList(RelatorCodes.getInstance());

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("country")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("b").setBibframeTag("state")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("c").setBibframeTag("county")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("d").setBibframeTag("city")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("e").setBibframeTag("relator")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("f").setBibframeTag("citySection")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("g").setBibframeTag("region")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("h").setBibframeTag("extraterrestrialArea")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify);
    getSubfield("2").setMqTag("source")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("4").setMqTag("relationship")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
