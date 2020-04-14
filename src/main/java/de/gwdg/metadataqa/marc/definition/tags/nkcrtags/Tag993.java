package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag993 extends DataFieldDefinition {

  private static Tag993 uniqueInstance;

  private Tag993() {
    initialize();
    postCreation();
  }

  public static Tag993 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag993();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "993";
    label = "Holdingové informace (Clavius)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Nákladová cena svazku (ve tvaru 99999.99)", "NR",
      "c", "Přírůstkové číslo", "NR",
      "d", "Datum vložení svazku (ve tvaru RRRRMMDD)", "NR",
      "e", "Exemplář periodika (jednoznakový 1..9, A..Ž)", "NR",
      "g", "Svazková signatura / označení čísla periodika", "NR",
      "h", "Cena svazku (ve tvaru 99999.99)", "NR",
      "i", "Dodavatel", "NR",
      "j", "Číslo dodacího listu nebo příjemky", "NR",
      "k", "Skladová signatura / ročník periodika", "NR",
      "l", "Lokace/umístění", "NR",
      "m", "Počet kusů periodika", "NR",
      "n", "Způsob nabytí (K - koupě,D - dary,J - jiný způsob)", "NR",
      "p", "Svazková poznámka (neomezena délka)", "NR",
      "r", "Rok vydaní periodika", "NR",
      "s", "Datum vyřazení svazku (ve tvaru RRRRMMDD)", "NR",
      "t", "Tématická skupina", "NR",
      "u", "Úbytkové číslo svazku", "NR",
      "v", "Druh vazby (1 písmenná zkratka) / periodicita periodika", "NR",
      "w", "Pořadí periodika v roce", "NR",
      "x", "Kategorie", "NR",
      "z", "Zpracovatel (dvou písmenná zkratka)", "NR",
      "1", "Čárový kód", "NR"
    );

    getSubfield("n")
      .setCodes(
      "K", "Koupě",
      "D", "Dary",
      "J", "Jiný způsob"
      );
  }
}
