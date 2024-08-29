package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Selektionskennzeichen EBooks, Digitalisate
 */
public class Tag962 extends DataFieldDefinition {

  private static Tag962 uniqueInstance;

  private Tag962() {
    initialize();
    postCreation();
  }

  public static Tag962 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag962();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "962";
    label = "Selektionskennzeichen EBooks, Digitalisate";
    mqTag = "SelektionskennzeichenEBooksDigitalisate";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/display/VDBE/962+-+Selektionskennzeichen+EBooks%2C+Digitalisate";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "e", "Selektionskennzeichen EBooks, Digitalisate", "R"
    );

  }
}
