package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Notes Relating to an Original (RPS)
 */
public class Tag599 extends DataFieldDefinition {

  private static Tag599 uniqueInstance;

  private Tag599() {
    initialize();
    postCreation();
  }

  public static Tag599 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag599();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "599";
    label = "Notes Relating to an Original (RPS)";
    mqTag = "NotesRelatingToAnOriginal";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Note relating to the copy filmed", "NR"
    );

    getSubfield("a")
      .setMqTag("note");
  }
}
