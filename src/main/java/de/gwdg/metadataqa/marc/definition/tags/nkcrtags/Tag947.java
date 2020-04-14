package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag947 extends DataFieldDefinition {

  private static Tag947 uniqueInstance;

  private Tag947() {
    initialize();
    postCreation();
  }

  public static Tag947 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag947();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "947";
    label = "Zdrojový a recenzovaný dokument – variantní názvy (analytický popis)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Typ názvu")
    .setCodes(
      " ", "Typ názvu není specifikován",
      "0", "Část názvu",
      "1", "Souběžný název",
      "2", "Rozlišovací název",
      "3", "Další variantní název"
    );
    ind2 = new Indicator("Druh dokumentu")
    .setCodes(
      "0", "Zdrojový dokument",
      "1", "Recenzovaný dokument"
    );

    setSubfieldsWithCardinality(
      "a", "Název", "R",
      "4", "Druh dokumentu (pouze u rec. dok.)", "NR",
      "a", "Chronologický údaj nebo datum vydání (pouze u zdroj. dok.)", "NR"
    );
  }
}
