package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag980 extends DataFieldDefinition {

  private static Tag980 uniqueInstance;

  private Tag980() {
    initialize();
    postCreation();
  }

  public static Tag980 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag980();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "980";
    label = "Údaje o excerpovaných (nebo analyticky zpracovávaných) titulech (pro Národní bibliografii – rezervované pole)";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Instituce analyzující seriál pro excerpční základnu", "R",
      "b", "Instituce analyzující seriál pro vlastní potřebu", "R",
      "c", "Číslo/rozmezí čísel", "NR",
      "k", "Kategorie", "NR",
      "d", "Poznámka", "R",
      "r", "Roky excerpce", "NR"
    );
  }
}
