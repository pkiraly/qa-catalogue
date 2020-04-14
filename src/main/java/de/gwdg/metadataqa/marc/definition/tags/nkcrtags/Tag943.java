package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag943 extends DataFieldDefinition {

  private static Tag943 uniqueInstance;

  private Tag943() {
    initialize();
    postCreation();
  }

  public static Tag943 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag943();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "943";
    label = "Interní poznámka (analytický popis)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Poznámka", "NR"
    );
  }
}
