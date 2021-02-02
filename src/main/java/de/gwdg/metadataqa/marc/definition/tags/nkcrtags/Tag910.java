package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Location data for the Union catalog
 * https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21
 * https://www.caslin.cz/caslin/spoluprace/jak-prispivat-do-sk-cr/standardy/spravny-zapis-udaju-v-poli-910-a-v-poli-911
 */
public class Tag910 extends DataFieldDefinition {

  private static Tag910 uniqueInstance;

  private Tag910() {
    initialize();
    postCreation();
  }

  public static Tag910 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag910();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "910";
    label = "Location data for the Union catalog";
    mqTag = "LocationDataUnionCatalog";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("M");

    ind1 = new Indicator()
      .setCodes(
        "1", "NK ČR",
        "2", "MZK",
        "3", "VKOL"
      );
    ind2 = new Indicator()
      .setCodes(
        " ", "standardní hodnota",
        "1", "MZK",
        "2", "VKOL",
        "3", "VKOL"
      );

    setSubfieldsWithCardinality(
      "a", "Sigla", "NR",
      "b", "Signatura", "R",
      "c", "Signatura nevázaných periodik", "R",
      "d", "Příznam pro DDS", "NR",
      "k", "Statistika retrokonverze", "NR",
      "l", "Roky odběru u seriálů, které jsou v knihovně přístupné on-line", "NR",
      "o", "Rok aktualizace odběru", "NR",
      "p", "Poznámka (prezenčně, neúplné atd.)", "NR",
      "q", "Kód revize údajů", "NR",
      "r", "Roky odběru (rozpětí let)", "NR",
      "s", "Svazky (volume, Band...)", "NR",
      "t", "Typ dokumentu", "NR",
      "u", "Doba uchování", "NR",
      "x", "Identifikační číslo zdrojového záznamu - používá správce SK ČR", "NR",
      "w", "Propojovací URL adresa na záznam - používá správce SK ČR", "R"
    );

    getSubfield("a")
      .setCompilanceLevels("M");

    getSubfield("k").setCodes(
      "r", "retrokonverze",
      "k", "převzatý záznam"
    );
    
    getSubfield("r")
      .setCompilanceLevels("A");

    getSubfield("s")
      .setCompilanceLevels("A");

    getSubfield("t").setCodes(
      "p", "pravá periodika",
      "n", "nepravá periodika",
      "rm", "monografie (ANL)",
      "rs", "seriály (ANL)",
      "rd", "deníky (ANL)",
      "rk", "artografické dokumenty (ANL)",
      "rg", "grafika (ANL)"
    );
  }
}