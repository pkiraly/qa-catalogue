package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag969 extends DataFieldDefinition {

  private static Tag969 uniqueInstance;

  private Tag969() {
    initialize();
    postCreation();
  }

  public static Tag969 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag969();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "969";
    label = "Tematické třídění UNESCO";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Notace", "R"
    );
  }
}
