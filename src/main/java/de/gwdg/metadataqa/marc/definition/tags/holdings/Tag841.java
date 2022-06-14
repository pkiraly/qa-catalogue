package de.gwdg.metadataqa.marc.definition.tags.holdings;

import de.gwdg.metadataqa.marc.definition.Cardinality;
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
      .setMqTag("typeOfRecord");

    getSubfield("a").setPositions(Arrays.asList(
        new ControlfieldPositionDefinition("Type of record", 0, 1),
        new ControlfieldPositionDefinition("Undefined", 1, 3),
        new ControlfieldPositionDefinition("Character coding scheme", 3, 4)
      ));

    getSubfield("b")
      .setMqTag("fixedLengthDataElements");

    getSubfield("b").setPositions(Arrays.asList(
      new ControlfieldPositionDefinition("Holdings 008 values", 0, 32)));

    getSubfield("e")
      .setMqTag("encodingLevel");

    getSubfield("e").setPositions(Arrays.asList(
      new ControlfieldPositionDefinition("Encoding level", 0, 1)));
  }
}
