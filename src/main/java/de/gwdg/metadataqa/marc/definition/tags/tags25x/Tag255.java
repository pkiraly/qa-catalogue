package de.gwdg.metadataqa.marc.definition.tags.tags25x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Cartographic Mathematical Data
 * http://www.loc.gov/marc/bibliographic/bd255.html
 */
public class Tag255 extends DataFieldDefinition {
  private static Tag255 uniqueInstance;

  private Tag255() {
    initialize();
    postCreation();
  }

  public static Tag255 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag255();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "255";
    label = "Cartographic Mathematical Data";
    bibframeTag = "Cartographic";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd255.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Statement of scale", "NR",
      "b", "Statement of projection", "NR",
      "c", "Statement of coordinates", "NR",
      "d", "Statement of zone", "NR",
      "e", "Statement of equinox", "NR",
      "f", "Outer G-ring coordinate pairs", "NR",
      "g", "Exclusion G-ring coordinate pairs", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("scale")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("b").setBibframeTag("projection")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, UseInterpret);
    getSubfield("c").setBibframeTag("coordinates")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect);
    getSubfield("d").setBibframeTag("ascensionAndDeclination")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect);
    getSubfield("e").setBibframeTag("equinox")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect);
    getSubfield("f").setBibframeTag("outerGRing");
    getSubfield("g").setBibframeTag("exclusionGRing")
      .setFrbrFunctions(DiscoverySearch, DiscoveryIdentify, DiscoverySelect);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
