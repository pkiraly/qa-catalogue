package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.ManagementProcess;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseManage;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Physical Description
 * https://www.loc.gov/marc/bibliographic/bd300.html
 */
public class Tag300 extends DataFieldDefinition {
  private static Tag300 uniqueInstance;

  private Tag300() {
    initialize();
    postCreation();
  }

  public static Tag300 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag300();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "300";
    label = "Physical Description";
    mqTag = "PhysicalDescription";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd300.html";
    setCompilanceLevels("M", "M");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Extent", "R",
      "b", "Other physical details", "NR",
      "c", "Dimensions", "R",
      "e", "Accompanying material", "NR",
      "f", "Type of unit", "R",
      "g", "Size of unit", "R",
      "3", "Materials specified", "NR",
      "6", "Linkage", "NR",
      "7", "Data provenance", "R",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("extent")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
      .setCompilanceLevels("M", "M");

    getSubfield("b")
      .setBibframeTag("note").setMqTag("otherPhysicalDetails")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setBibframeTag("dimensions")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate)
      .setCompilanceLevels("M");

    getSubfield("e")
      .setBibframeTag("note").setMqTag("accompanyingMaterial")
      .setFrbrFunctions(DiscoverySelect)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("typeOfUnit")
      .setFrbrFunctions(UseManage, UseOperate)
      .setCompilanceLevels("A", "A");

    getSubfield("g")
      .setMqTag("sizeOfUnit")
      .setFrbrFunctions(UseManage, UseOperate)
      .setCompilanceLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setCompilanceLevels("O");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A", "A");

    getSubfield("7")
      .setMqTag("dataProvenance");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");

    setHistoricalSubfields(
      "d", "Accompanying material [OBSOLETE, 1997] [CAN/MARC only]",
      "m", "Identification/manufacturer number [pre-AACR2 records only] [OBSOLETE, 1988] [CAN/MARC only]",
      "n", "Matrix and/or take number [Sound recordings, pre-AACR2 records only] [OBSOLETE, 1988] [CAN/MARC only]"
    );

    putVersionSpecificSubfields(MarcVersion.KBR, Arrays.asList(
      new SubfieldDefinition("*", "Link with identifier", "NR").setMqTag("link"),
      new SubfieldDefinition("@", "Language of field", "NR").setMqTag("language"),
      new SubfieldDefinition("#", "number/occurrence of field", "NR").setMqTag("number")
    ));
  }
}
