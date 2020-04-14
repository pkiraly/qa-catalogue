package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag949 extends DataFieldDefinition {

  private static Tag949 uniqueInstance;

  private Tag949() {
    initialize();
    postCreation();
  }

  public static Tag949 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag949();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "949";
    label = "Zdrojový dokument a recenzovaný dokument – edice –  korporace  (analytický popis)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Forma jména")
    .setCodes(
      " ", "Nedefinována",
      "0", "Invertovaná forma jména",
      "1", "Jméno jurisdikce",
      "2", "Jméno v přímém pořadí"
    );
    ind2 = new Indicator("Druh dokumentu")
    .setCodes(
      "0", "Zdrojový dokument",
      "1", "Recenzovaný dokument"
    );

    setSubfieldsWithCardinality(
      "a", "Korporace", "NR",
      "t", "Název", "NR",
      "x", "Mezinárodní standardní číslo", "R",
      "v", "Označení svazku", "R",
      "4", "Druh dokumentu (pouze u rec. dok.)", "NR",
      "9", "Chronologický údaj nebo datum vydání (pouze u zdroj. dok.)", "NR"
    );
  }
}
