package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag998 extends DataFieldDefinition {

  private static Tag998 uniqueInstance;

  private Tag998() {
    initialize();
    postCreation();
  }

  public static Tag998 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag998();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "998";
    label = "Šifra zpracovatele (pracovní pole retrokonverze)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Šifra zpracovatele", "NR",
      "b", "Šifra revidujícího", "NR"
    );
  }
}
