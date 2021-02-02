package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * LEO (Library Export Operations) Identifier
 */
public class TagLEO extends DataFieldDefinition {

  private static TagLEO uniqueInstance;

  private TagLEO() {
    initialize();
    postCreation();
  }

  public static TagLEO getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagLEO();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "LEO";
    label = "LEO (Library Export Operations) Identifier";
    mqTag = "LibraryExportOperationsIdentifier";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "LEO identifier", "NR"
    );

    getSubfield("a").setMqTag("identifier");
  }
}
