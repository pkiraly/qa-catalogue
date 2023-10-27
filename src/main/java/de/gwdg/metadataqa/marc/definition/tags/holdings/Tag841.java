package de.gwdg.metadataqa.marc.definition.tags.holdings;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader06;
import de.gwdg.metadataqa.marc.definition.controlpositions.leader.Leader17;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

import java.util.Arrays;

/**
 * Holding Institution
 * https://www.loc.gov/marc/holdings/hd841.html
 */
public class Tag841 extends DataFieldDefinition {

  private static Tag841 uniqueInstance;

  private Tag841() {
    initialize();
    postCreation();
  }

  public static Tag841 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag841();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "841";
    label = "Holdings Coded Data Values";
    mqTag = "HoldingsCodedDataValues";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.loc.gov/marc/holdings/hd841.html";

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Type of record", "NR",
      "b", "Fixed-length data elements", "NR",
      "e", "Encoding level", "NR"
    );

    getSubfield("a")
      .setMqTag("typeOfRecord")
      .setPositions(Arrays.asList(
        new ControlfieldPositionDefinition("Type of record", 0, 1)
          .setCodeListReference(Leader06.getInstance()),
        new ControlfieldPositionDefinition("Undefined", 1, 3)
          .hasCodelist(false),
        new ControlfieldPositionDefinition("Character coding scheme", 3, 4)
          .setCodeListReference(Leader17.getInstance())
      ));

    getSubfield("b")
      .setMqTag("fixedLengthDataElements")
      // TODO: this should be the same as the whole 008
      .setPositions(Arrays.asList(
        new ControlfieldPositionDefinition("Holdings 008 values", 0, 32)));

    getSubfield("e")
      .setMqTag("encodingLevel")
      .setPositions(Arrays.asList(
        new ControlfieldPositionDefinition("Encoding level", 0, 1)
          .setCodeListReference(Leader17.getInstance())));
  }
}
