package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag966 extends DataFieldDefinition {

  private static Tag966 uniqueInstance;

  private Tag966() {
    initialize();
    postCreation();
  }

  public static Tag966 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag966();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "966";
    label = "Číslo vědního oboru (disertace)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Číslo vědního oboru", "R"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
