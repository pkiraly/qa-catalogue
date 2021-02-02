package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Non-monographic Conference Indicator
 */
public class Tag976 extends DataFieldDefinition {

  private static Tag976 uniqueInstance;

  private Tag976() {
    initialize();
    postCreation();
  }

  public static Tag976 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag976();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "976";
    label = "Non-monographic Conference Indicator";
    mqTag = "NonmonographicConferenceIndicator";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Non-monographic conference indicator", "NR"
    );

    getSubfield("a")
      .setCodes(
        "p", "non-monographic conference record"
      )
      .setMqTag("indicator");
  }
}
