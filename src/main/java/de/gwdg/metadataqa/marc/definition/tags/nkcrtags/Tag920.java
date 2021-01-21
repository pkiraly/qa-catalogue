package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag920 extends DataFieldDefinition {

  private static Tag920 uniqueInstance;

  private Tag920() {
    initialize();
    postCreation();
  }

  public static Tag920 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag920();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "920";
    label = "Zrušené identifikační číslo záznamu";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Identifikační číslo", "NR",
      "z", "Sigla knihovny", "NR"
    );
    getSubfield("a")
      .setCompilanceLevels("M");
    getSubfield("z")
      .setCompilanceLevels("O");
  }
}
