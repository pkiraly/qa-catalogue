package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag912 extends DataFieldDefinition {

  private static Tag912 uniqueInstance;

  private Tag912() {
    initialize();
    postCreation();
  }

  public static Tag912 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag912();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "912";
    label = "Excerpce";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Sigla", "NR",
      "d", "Úroveň excerpce", "NR",
      "r", "Roky", "NR",
      "s", "Svazky (rezervováno)", "NR",
      "p", "Poznámka", "NR",
      "u", "Odkaz", "R"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
