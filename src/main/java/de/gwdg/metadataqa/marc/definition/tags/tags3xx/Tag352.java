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
    setLevels("A");

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

    getSubfield("a")
      .setBibframeTag("cartographicDataType")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("b")
      .setBibframeTag("cartographicObjectType")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("c")
      .setBibframeTag("count").setMqTag("objectCount")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("rowCount")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("columnCount")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("f")
      .setMqTag("verticalCount")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("g")
      .setMqTag("topologyLevel")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("i")
      .setMqTag("indirectReference")
      .setFrbrFunctions(UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("q")
      .setBibframeTag("encodingFormat")
      .setFrbrFunctions(DiscoveryIdentify, UseOperate, UseInterpret)
      .setLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");
  }
}
