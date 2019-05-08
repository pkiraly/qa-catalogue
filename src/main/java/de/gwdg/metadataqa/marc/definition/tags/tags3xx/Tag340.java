package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Physical Medium
 * http://www.loc.gov/marc/bibliographic/bd340.html
 */
public class Tag340 extends DataFieldDefinition {
  private static Tag340 uniqueInstance;

  private Tag340() {
    initialize();
    postCreation();
  }

  public static Tag340 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag340();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "340";
    label = "Physical Medium";
    mqTag = "PhysicalMedium";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd340.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Material base and configuration", "R",
      "b", "Dimensions", "R",
      "c", "Materials applied to surface", "R",
      "d", "Information recording technique", "R",
      "e", "Support", "R",
      "f", "Production rate/ratio", "R",
      "g", "Color content", "R",
      "h", "Location within medium", "R",
      "i", "Technical specifications of medium", "R",
      "j", "Generation", "R",
      "k", "Layout", "R",
      "m", "Book format", "R",
      "n", "Font size", "R",
      "o", "Polarity", "R",
      "0", "Authority record control number or standard number", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("baseMaterial")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
    getSubfield("b").setBibframeTag("dimensions")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
    getSubfield("c").setBibframeTag("appliedMaterial")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
    getSubfield("d").setMqTag("productionMethod")
      .setFrbrFunctions(DiscoverySelect, UseManage, UseOperate);
    getSubfield("e").setMqTag("mount")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage);
    getSubfield("f").setMqTag("reductionRatio")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
    getSubfield("g").setMqTag("colorContent");
    getSubfield("h").setMqTag("location")
      .setFrbrFunctions(DiscoveryIdentify, UseManage);
    getSubfield("i").setMqTag("systemRequirement")
      .setFrbrFunctions(DiscoverySelect, UseOperate);
    getSubfield("j").setMqTag("generation");
    getSubfield("k").setMqTag("layout");
    getSubfield("m").setMqTag("bookFormat");
    getSubfield("n").setMqTag("fontSize");
    getSubfield("o").setMqTag("polarity");
    getSubfield("0").setMqTag("authorityRecordControlNumber");
    getSubfield("2").setBibframeTag("source");
    getSubfield("3").setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
