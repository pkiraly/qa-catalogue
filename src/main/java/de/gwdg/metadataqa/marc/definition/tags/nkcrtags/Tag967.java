package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag967 extends DataFieldDefinition {

  private static Tag967 uniqueInstance;

  private Tag967() {
    initialize();
    postCreation();
  }

  public static Tag967 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag967();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "967";
    label = "Regionální třídění (pouze pro konverzi)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Geografický název", "NR",
      "b", "Okres", "R",
      "c", "Nadřazená územní jednotka", "R"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
