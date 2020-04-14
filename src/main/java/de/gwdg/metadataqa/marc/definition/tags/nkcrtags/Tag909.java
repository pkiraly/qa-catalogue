package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag909 extends DataFieldDefinition {

  private static Tag909 uniqueInstance;

  private Tag909() {
    initialize();
    postCreation();
  }

  public static Tag909 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag909();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "909";
    label = "Systémové číslo záznamu (SK CASLIN)";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();
  }
}