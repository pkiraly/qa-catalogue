package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Local Fixed Field - Music
 */
public class Tag909 extends DataFieldDefinition {
  private static Tag909 uniqueInstance;

  private Tag909() {
    initialize();
    postCreation();
  }

  public static Tag909 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag909();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "909";
    label = "Local Fixed Field - Music";
    mqTag = "LocalFixedFieldMusic";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Coded data on instrumental strength (sheet music)", "NR",
      "b", "Coded date(s) of composition (sheet music)", "NR",
      "c", "Coded notation (sheet music)", "NR"
    );

    getSubfield("a").setMqTag("CodedDataOnInstrumentalStrength");
    getSubfield("b").setMqTag("CodedDatesOfComposition");
    getSubfield("c").setMqTag("CodedNotation");
  }
}
