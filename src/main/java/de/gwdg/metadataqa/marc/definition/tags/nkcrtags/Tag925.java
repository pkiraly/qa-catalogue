package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag925 extends DataFieldDefinition {

  private static Tag925 uniqueInstance;

  private Tag925() {
    initialize();
    postCreation();
  }

  public static Tag925 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag925();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "925";
    label = "Obsazení (hudební díla)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Obsazení (slovy)", "R",
      "b", "Obsazení v kód. tvaru", "R",
      "c", "Sóla v kód. tvaru", "R"
    );
  }
}
