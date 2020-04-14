package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag951 extends DataFieldDefinition {

  private static Tag951 uniqueInstance;

  private Tag951() {
    initialize();
    postCreation();
  }

  public static Tag951 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag951();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "951";
    label = "Zdrojový dokument a recenzovaný dokument";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    
    ind1 = new Indicator("Indikátor formy jména")
      .setCodes(
        " ", "Undefined"
      );
    ind2 = new Indicator("Druh dokumentu")
      .setCodes(
        "0", "Zdrojový dokument",
        "1", "Recenzovaný dokument"
      );

    setSubfieldsWithCardinality(
      "a", "Unifikovaný název", "NR",
      "t", "Název", "NR",
      "x", "Mezinárodní standardní číslo", "R",
      "v", "Označení svazku", "R",
      "4", "Druh dokumentu (pouze u rec. dok.)", "NR",
      "9", "Chronologický údaj nebo datum vydání (pouze u zdroj. dok.)", "NR"
    );
  }
}
