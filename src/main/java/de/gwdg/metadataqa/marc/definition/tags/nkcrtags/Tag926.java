package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag926 extends DataFieldDefinition {

  private static Tag926 uniqueInstance;

  private Tag926() {
    initialize();
    postCreation();
  }

  public static Tag926 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag926();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "926";
    label = "Číselné označení skladby";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Číslo tématického katalogu", "R",
      "b", "Opusové číslo", "R"
    );
  }
}
