package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag972 extends DataFieldDefinition {

  private static Tag972 uniqueInstance;

  private Tag972() {
    initialize();
    postCreation();
  }

  public static Tag972 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag972();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "972";
    label = " Signatura NKCF (pracovní pole pro retrokonverzi)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Signatura", "NR"
    );
  }
}
