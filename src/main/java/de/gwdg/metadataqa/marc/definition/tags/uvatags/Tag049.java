package de.gwdg.metadataqa.marc.definition.tags.uvatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

/**
 * Abbreviated Title
 * http://www.loc.gov/marc/bibliographic/bd210.html
 */
public class Tag049 extends DataFieldDefinition {
  private static Tag049 uniqueInstance;

  private Tag049() {
    initialize();
    postCreation();
  }

  public static Tag049 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag049();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "049";
    label = "Local holdings";
    bibframeTag = "LocalHoldings";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = null;

    ind1 = null;
    ind2 = null;

    setSubfieldsWithCardinality(
      "a", "OCLC Institution Symbol", "NR"
    );

    getSubfield("a")
      .setMqTag("OCLCInstitutionSymbol");
  }
}
