package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag928 extends DataFieldDefinition {

  private static Tag928 uniqueInstance;

  private Tag928() {
    initialize();
    postCreation();
  }

  public static Tag928 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag928();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "928";
    label = "Nakladatel pro záznamy CIP (dříve pro soubor autorit)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");
    
    ind1 = new Indicator()
      .setCodes(
        "1", "Knihy ohlášené NA ISBN a CIP",
        "9", "Pomocný indikátor odlišující v databázi formalizovanou úpravu jména nakladatele"
      );
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Vstupní prvek", "NR",
      "b", "Zpřesnění", "R",
      "c", "Doplňky ke jménu a/nebo kvalifikátor", "R",
      "g", "Invertovaný prvek", "NR",
      "h", "Část jména jiná než vstupní nebo invertovaný prvek", "R",
      "4", "Kód role", "R"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
