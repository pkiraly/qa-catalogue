package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Digital Graphic Representation
 * http://www.loc.gov/marc/bibliographic/bd352.html
 */
public class Tag352 extends DataFieldDefinition {
  private static Tag352 uniqueInstance;

  private Tag352() {
    initialize();
    postCreation();
  }

  public static Tag352 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag352();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "352";
    label = "Digital Graphic Representation";
    mqTag = "DigitalGraphicRepresentation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd352.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Direct reference method", "NR",
      "b", "Object type", "R",
      "c", "Object count", "R",
      "d", "Row count", "NR",
      "e", "Column count", "NR",
      "f", "Vertical count", "NR",
      "g", "VPF topology level", "NR",
      "i", "Indirect reference description", "NR",
      "q", "Format of the digital image", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a").setBibframeTag("cartographicDataType")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("b").setBibframeTag("cartographicObjectType")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("c").setBibframeTag("count").setMqTag("objectCount")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("d").setMqTag("rowCount")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("e").setMqTag("columnCount")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("f").setMqTag("verticalCount")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("g").setMqTag("topologyLevel")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("i").setMqTag("indirectReference")
      .setFrbrFunctions(UsageOperate, UsageInterpret);
    getSubfield("q").setBibframeTag("encodingFormat")
      .setFrbrFunctions(DiscoveryIdentify, UsageOperate, UsageInterpret);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
