package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag931 extends DataFieldDefinition {

  private static Tag931 uniqueInstance;

  private Tag931() {
    initialize();
    postCreation();
  }

  public static Tag931 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag931();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "931";
    label = "Úroveň zdroje";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Úroveň zdroje", "NR"
    );
  }
}
