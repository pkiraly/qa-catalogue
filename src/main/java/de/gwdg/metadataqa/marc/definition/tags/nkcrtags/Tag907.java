package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag907 extends DataFieldDefinition {

  private static Tag907 uniqueInstance;

  private Tag907() {
    initialize();
    postCreation();
  }

  public static Tag907 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag907();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "907";
    label = "Národní kód jazyka";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Kód jazyka popisné jednotky", "NR",
      "b", "Kód jazyka originálu", "NR"
    );
  }
}
