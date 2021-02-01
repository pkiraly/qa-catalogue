package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Document Supply Conference Note
 */
public class Tag591 extends DataFieldDefinition {

  private static Tag591 uniqueInstance;

  private Tag591() {
    initialize();
    postCreation();
  }

  public static Tag591 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag591();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "591";
    label = "Document Supply Conference Note";
    mqTag = "DocumentSupplyConferenceNote";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Conference note", "NR"
    );

    getSubfield("a")
      .setMqTag("conferenceNote");
  }
}
