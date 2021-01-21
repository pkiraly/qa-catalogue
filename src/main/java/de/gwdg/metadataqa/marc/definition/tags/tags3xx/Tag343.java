package de.gwdg.metadataqa.marc.definition.tags.tags3xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
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
    setCompilanceLevels("A");

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

    getSubfield("a")
      .setMqTag("method")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("b")
      .setMqTag("distanceUnits")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("c")
      .setMqTag("abscissa")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("d")
      .setMqTag("ordinate")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("e")
      .setMqTag("distance")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("f")
      .setMqTag("bearingResolution")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("g")
      .setMqTag("bearingUnits")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("h")
      .setMqTag("bearingDirection")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("i")
      .setMqTag("bearingMeridian")
      .setFrbrFunctions(UseInterpret)
      .setCompilanceLevels("A");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setCompilanceLevels("O");
  }
}
