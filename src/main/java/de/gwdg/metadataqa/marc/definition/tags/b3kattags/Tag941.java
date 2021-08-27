package de.gwdg.metadataqa.marc.definition.tags.b3kattags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Strukturierte Quellenangabe (unselbständige Publikationen)
 * https://www.bib-bvb.de/web/b3kat/open-data
 */
public class Tag941 extends DataFieldDefinition {

  private static Tag941 uniqueInstance;

  private Tag941() {
    initialize();
    postCreation();
  }

  public static Tag941 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag941();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "941";
    label = "Strukturierte Quellenangabe (unselbständige Publikationen)";
    mqTag = "StrukturierteQuellenangabe";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.bib-bvb.de/web/b3kat/open-data";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "b", "", "NR",
      "h", "", "NR",
      "j", "", "NR",
      "m", "", "NR",
      "t", "", "NR",
      "r", "", "NR",
      "s", "", "NR"
    );
  }
}
