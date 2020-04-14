package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag948 extends DataFieldDefinition {

  private static Tag948 uniqueInstance;

  private Tag948() {
    initialize();
    postCreation();
  }

  public static Tag948 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag948();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "948";
    label = "Zdrojový a recenzovaný dokument  – edice – osobní jméno (analytický popis)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator("Forma jména")
    .setCodes(
      " ", "Nedefinována",
      "0", "Jméno se uvádí pod rodným/křestním jménem",
      "1", "Jméno se uvádí pod příjmením",
      "3", "Jméno rodiny/rodu"
    );
    ind2 = new Indicator("Druh dokumentu")
    .setCodes(
      "0", "Zdrojový dokument",
      "1", "Recenzovaný dokument"
    );

    setSubfieldsWithCardinality(
      "a", "Osobní jméno", "NR",
      "t", "Název", "NR",
      "x", "Mezinárodní standardní číslo", "R",
      "v", "Označení svazku", "R",
      "4", "Druh dokumentu (pouze u rec. dok.)", "NR",
      "9", "Chronologický údaj nebo datum vydání (pouze u zdroj. dok.)", "NR"
    );
  }
}
