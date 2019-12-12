package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Physical Description
 * http://www.loc.gov/marc/bibliographic/bd300.html
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
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("extent")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect)
      .setLevels("M", "M");

    getSubfield("b")
      .setBibframeTag("note").setMqTag("otherPhysicalDetails")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseManage, UseOperate)
      .setLevels("A");

    getSubfield("c")
      .setBibframeTag("dimensions")
      .setFrbrFunctions(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate)
      .setLevels("M");

    getSubfield("e")
      .setBibframeTag("note").setMqTag("accompanyingMaterial")
      .setFrbrFunctions(DiscoverySelect)
      .setLevels("A");

    getSubfield("f")
      .setMqTag("typeOfUnit")
      .setFrbrFunctions(UseManage, UseOperate)
      .setLevels("A", "A");

    getSubfield("g")
      .setMqTag("sizeOfUnit")
      .setFrbrFunctions(UseManage, UseOperate)
      .setLevels("A");

    getSubfield("3")
      .setMqTag("materialsSpecified")
      .setFrbrFunctions(DiscoveryIdentify)
      .setLevels("O");

    getSubfield("6")
      .setMqTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A", "A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");

    setHistoricalSubfields(
      "d", "Accompanying material [OBSOLETE, 1997] [CAN/MARC only]",
      "m", "Identification/manufacturer number [pre-AACR2 records only] [OBSOLETE, 1988] [CAN/MARC only]",
      "n", "Matrix and/or take number [Sound recordings, pre-AACR2 records only] [OBSOLETE, 1988] [CAN/MARC only]"
    );
  }
}
