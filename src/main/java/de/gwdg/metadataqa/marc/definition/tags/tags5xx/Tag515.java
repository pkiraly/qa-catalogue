package de.gwdg.metadataqa.marc.definition.tags.tags5xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * Numbering Peculiarities Note
 * http://www.loc.gov/marc/bibliographic/bd515.html
 */
public class Tag515 extends DataFieldDefinition {

  private static Tag515 uniqueInstance;

  private Tag515() {
    initialize();
    postCreation();
  }

  public static Tag515 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag515();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "515";
    label = "Numbering Peculiarities Note";
    mqTag = "NumberingPeculiarities";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd515.html";
    setLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Numbering peculiarities note", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("6").setContentParser(LinkageParser.getInstance());

    getSubfield("a")
      .setBibframeTag("rdfs:label").setMqTag("rdf:value")
      .setFrbrFunctions(UseManage)
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
      "z", "Source of note information (SE) [OBSOLETE, 1990]"
    );
  }
}
