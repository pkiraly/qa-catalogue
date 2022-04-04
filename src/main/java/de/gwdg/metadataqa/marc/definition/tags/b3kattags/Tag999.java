package de.gwdg.metadataqa.marc.definition.tags.b3kattags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * OAI-Identifier des Datensatzes
 * https://www.bib-bvb.de/web/b3kat/open-data
 */
public class Tag999 extends DataFieldDefinition {

  private static Tag999 uniqueInstance;

  private Tag999() {
    initialize();
    postCreation();
  }

  public static Tag999 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag999();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "999";
    label = "OAI-Identifier des Datensatzes";
    mqTag = "OAIIdentifier";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.bib-bvb.de/web/b3kat/open-data";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "OAI-Identifier", "NR"
    );

    getSubfield("a").setMqTag("OAIIdentifier");
  }
}
