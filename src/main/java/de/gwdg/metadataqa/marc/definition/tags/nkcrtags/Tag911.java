package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

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
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Sigla knihovny, která dokument digitalizovala.", "NR",
      "d", "Status digitalizace (digitalizuje se a zdigitalizováno)", "NR",
      "r", "Roky ( u časopisů roky, které byly zdigitalizovány)", "NR",
      "s", "Svazky (i u knih i u seriálů) ", "NR",
      "p", "Url adresa, na které je dostupná zdigitaliz. kopie", "R",
      "u", "Poznámka", "NR"
    );

    getSubfield("a")
      .setCompilanceLevels("M");

    getSubfield("b")
      .setCompilanceLevels("M");
  }
}
