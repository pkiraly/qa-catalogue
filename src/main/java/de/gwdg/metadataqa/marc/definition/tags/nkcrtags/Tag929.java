package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag929 extends DataFieldDefinition {

  private static Tag929 uniqueInstance;

  private Tag929() {
    initialize();
    postCreation();
  }

  public static Tag929 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag929();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "929";
    label = "Typ podle DCMI";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Typ", "NR"
    );
  }
}
