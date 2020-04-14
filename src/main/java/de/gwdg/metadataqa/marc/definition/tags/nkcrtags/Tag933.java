package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag933 extends DataFieldDefinition {

  private static Tag933 uniqueInstance;

  private Tag933() {
    initialize();
    postCreation();
  }

  public static Tag933 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag933();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "933";
    label = "Administrativní údaje pro oborové informační brány";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Sídlo vydavatele (česky)", "NR",
      "b", "Sídlo vydavatele (anglicky)", "NR",
      "c", "Mapování MDT – DDC: umístění tabulek (URL)", "NR",
      "d", "Umístění Z39.50:  IP adresa serveru", "NR",
      "e", "Umístění loga (URL)", "NR",
      "f", "Rozsah obor. brány-přibližný počet záznamů", "NR"
    );
  }
}
