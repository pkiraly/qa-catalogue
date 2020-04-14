package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag906 extends DataFieldDefinition {

  private static Tag906 uniqueInstance;

  private Tag906() {
    initialize();
    postCreation();
  }

  public static Tag906 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag906();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "906";
    label = "Sledování on-line oprav (SK ČR)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator("Type of personal name entry element")
      .setCodes("0", "Možnost oprav pověřenými knihovnami");

    setSubfieldsWithCardinality(
      "a", "Kód úpravy - datum ve fornalizované podobě (např. FU20110217)", "NR",
      "b", "Podpis (sigla knihovny nebo podpis správce SK ČR)", "NR"
    );
  }
}
