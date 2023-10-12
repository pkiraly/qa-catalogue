package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.general.parser.RecordControlNumberParser;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

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
    setCompilanceLevels("A");

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
      "1", "Real World Object URI", "R",
      "2", "Source", "NR",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("baseMaterial")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setBibframeTag("dimensions")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setBibframeTag("appliedMaterial")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("productionMethod")
      .setFrbrFunctions(DiscoverySelect, UseManage, UseOperate)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("mount")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("reductionRatio")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("colorContent");

    getSubfield("h")
      .setMqTag("location")
      .setFrbrFunctions(DiscoveryIdentify, UseManage)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("systemRequirement")
      .setFrbrFunctions(DiscoverySelect, UseOperate)
      .setCompilanceLevels("A");

    getSubfield("j")
      .setMqTag("generation");

    getSubfield("k")
      .setMqTag("layout");

    getSubfield("m")
      .setMqTag("bookFormat");

    getSubfield("n")
      .setMqTag("fontSize");

    getSubfield("o")
      .setMqTag("polarity");

    getSubfield("0")
      .setMqTag("authorityRecordControlNumber")
      .setContentParser(RecordControlNumberParser.getInstance());

    getSubfield("1")
      .setMqTag("uri");

    getSubfield("2")
      .setBibframeTag("source");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    putVersionSpecificSubfields(MarcVersion.NKCR, Arrays.asList(
      new SubfieldDefinition("7", "NKCR Authority ID", "NR")
    ));

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
