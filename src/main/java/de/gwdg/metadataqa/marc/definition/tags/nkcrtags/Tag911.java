package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag911 extends DataFieldDefinition {

  private static Tag911 uniqueInstance;

  private Tag911() {
    initialize();
    postCreation();
  }

  public static Tag911 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag911();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "911";
    label = "Informace o digitalizaci";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Sigla knihovny, která dokument digitalizovala", "NR",
      "d", "Status digitalizace (digitalizuje se a zdigitalizováno)", "NR",
      "r", "Roky (u časopisů roky, které byly zdigitalizovány)", "NR",
      "s", "Svazky (i u knih i u seriálů)", "NR",
      "p", "Url adresa, na které je dostupná zdigitaliz. kopie", "NR",
      "u", "Poznámka", "R"
    );

    getSubfield("a")
      .setCompilanceLevels("M");

    getSubfield("d")
      .setCodes(
        "zdigitalizováno", "hotová digitalizace",
        "digitalizuje se", "digitalizace probíhá, nebo již dokončena, ale ještě není digitální dokument dostupný",
        "vybráno k digitalizaci", "již k digitalizaci připraveno (je jisté, že se digitalizovat bude)",
        "plánovaná digitalizace", "rezervace titulu v delším časovém horizontu"
      )
      .setCompilanceLevels("M");
  }
}
