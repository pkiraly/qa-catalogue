package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Planar Coordinate Data
 * http://www.loc.gov/marc/bibliographic/bd343.html
 */
public class Tag343 extends DataFieldDefinition {
  private static Tag343 uniqueInstance;

  private Tag343() {
    initialize();
    postCreation();
  }

  public static Tag343 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag343();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "343";
    label = "Planar Coordinate Data";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd343.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Planar coordinate encoding method", "NR",
      "b", "Planar distance units", "NR",
      "c", "Abscissa resolution", "NR",
      "d", "Ordinate resolution", "NR",
      "e", "Distance resolution", "NR",
      "f", "Bearing resolution", "NR",
      "g", "Bearing units", "NR",
      "h", "Bearing reference direction", "NR",
      "i", "Bearing reference meridian", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    // TODO: setMqTag
    getSubfield("a")
      .setFrbrFunctions(UseInterpret);
    getSubfield("b")
      .setFrbrFunctions(UseInterpret);
    getSubfield("c")
      .setFrbrFunctions(UseInterpret);
    getSubfield("d")
      .setFrbrFunctions(UseInterpret);
    getSubfield("e")
      .setFrbrFunctions(UseInterpret);
    getSubfield("f")
      .setFrbrFunctions(UseInterpret);
    getSubfield("g")
      .setFrbrFunctions(UseInterpret);
    getSubfield("h")
      .setFrbrFunctions(UseInterpret);
    getSubfield("i")
      .setFrbrFunctions(UseInterpret);
    getSubfield("6").setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
    getSubfield("8").setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess);
  }
}
