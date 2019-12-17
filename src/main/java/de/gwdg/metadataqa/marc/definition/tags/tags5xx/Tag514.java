package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Data Quality Note
 * http://www.loc.gov/marc/bibliographic/bd514.html
 */
public class Tag514 extends DataFieldDefinition {

  private static Tag514 uniqueInstance;

  private Tag514() {
    initialize();
    postCreation();
  }

  public static Tag514 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag514();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "514";
    label = "Data Quality Note";
    mqTag = "DataQuality";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd514.html";
    setLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Attribute accuracy report", "NR",
      "b", "Attribute accuracy value", "R",
      "c", "Attribute accuracy explanation", "R",
      "d", "Logical consistency report", "NR",
      "e", "Completeness report", "NR",
      "f", "Horizontal position accuracy report", "NR",
      "g", "Horizontal position accuracy value", "R",
      "h", "Horizontal position accuracy explanation", "R",
      "i", "Vertical positional accuracy report", "NR",
      "j", "Vertical positional accuracy value", "R",
      "k", "Vertical positional accuracy explanation", "R",
      "m", "Cloud cover", "NR",
      "u", "Uniform Resource Identifier", "R",
      "z", "Display note", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("accuracyReport")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("b")
      .setMqTag("accuracyValue")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("c")
      .setMqTag("accuracyExplanation")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("d")
      .setMqTag("logicalConsistency")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("e")
      .setMqTag("completeness")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("f")
      .setMqTag("horizontalPositionAccuracyReport")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("g")
      .setMqTag("horizontalPositionAccuracyValue")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("h")
      .setMqTag("horizontalPositionAccuracyExplanation")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("i")
      .setMqTag("verticalPositionalAccuracyReport")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("j")
      .setMqTag("verticalPositionalAccuracyValue")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("k")
      .setMqTag("verticalPositionalAccuracyExplanation")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("m")
      .setMqTag("cloudCover")
      .setFrbrFunctions(UseInterpret)
      .setLevels("A");

    getSubfield("u")
      .setMqTag("uri")
      .setFrbrFunctions(DiscoveryObtain)
      .setLevels("O");

    getSubfield("z")
      .setMqTag("display")
      .setLevels("O");

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
