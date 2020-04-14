package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag952 extends DataFieldDefinition {

  private static Tag952 uniqueInstance;

  private Tag952() {
    initialize();
    postCreation();
  }

  public static Tag952 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag952();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "952";
    label = "Region, na jehož území je seriál vydáván (pro soubor autorit) (seriály)";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Region", "R"
    );
  }
}
