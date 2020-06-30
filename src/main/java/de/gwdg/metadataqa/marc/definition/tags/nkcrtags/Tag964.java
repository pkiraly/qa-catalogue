package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag964 extends DataFieldDefinition {

  private static Tag964 uniqueInstance;

  private Tag964() {
    initialize();
    postCreation();
  }

  public static Tag964 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag964();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "964";
    label = "Předmětová hesla podle staré metodiky (konverze a retrokonverze)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Předmětové heslo – řetězec", "NR",
      "b", "Konverze – personálie", "NR",
      "c", "Konverze – instituce – hud., zvuk.", "NR",
      "d", "Konverze – instituce – graf., obj.", "NR",
      "e", "Konverze – předm. hesla – zvuk.", "NR",
      "f", "Konverze – regionální třídění (graf. obj.)", "NR",
      "g", "Akce", "NR"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
