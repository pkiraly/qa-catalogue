package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag908 extends DataFieldDefinition {

  private static Tag908 uniqueInstance;

  private Tag908() {
    initialize();
    postCreation();
  }

  public static Tag908 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag908();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "908";
    label = "Typ speciálního dokumentu ";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Typ notového zápisu", "R",
      "b", "Typ zvukového záznamu", "R",
      "c", "Typ filmu/videozáznamu", "R"
    );
  }
}
