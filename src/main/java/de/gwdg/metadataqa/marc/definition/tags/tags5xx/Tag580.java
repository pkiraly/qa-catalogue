package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Linking Entry Complexity Note
 * http://www.loc.gov/marc/bibliographic/bd580.html
 */
public class Tag580 extends DataFieldDefinition {

  private static Tag580 uniqueInstance;

  private Tag580() {
    initialize();
    postCreation();
  }

  public static Tag580 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag580();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "580";
    label = "Linking Entry Complexity Note";
    mqTag = "LinkingEntryComplexity";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd580.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Linking entry complexity note", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setMqTag("rdf:value")
      .setLevels("M");

    getSubfield("6")
      .setBibframeTag("linkage")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("A");

    getSubfield("8")
      .setMqTag("fieldLink")
      .setFrbrFunctions(ManagementIdentify, ManagementProcess)
      .setLevels("O");

    setHistoricalSubfields(
      "z", "Source of note information [OBSOLETE, 1990]"
    );

    putVersionSpecificSubfields(MarcVersion.SZTE, Arrays.asList(
      new SubfieldDefinition("5", "Az intézmény és példány, amelyre a megjegyzés vonatkozik", "NR")
    ));

  }
}
