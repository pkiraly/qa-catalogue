package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag970 extends DataFieldDefinition {

  private static Tag970 uniqueInstance;

  private Tag970() {
    initialize();
    postCreation();
  }

  public static Tag970 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag970();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "970";
    label = "Sídlo vydavatele";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "n", "Název", "NR",
      "p", "Příjmení", "NR",
      "k", "Křestní jméno", "NR",
      "c", "Doplňky ke jménu", "NR",
      "a", "Adresa", "NR",
      "e", "Telefon", "NR",
      "f", "Fax", "NR",
      "x", "E-mail", "NR",
      "y", "Datum aktualizace", "NR"
    );
  }
}
