package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag960 extends DataFieldDefinition {

  private static Tag960 uniqueInstance;

  private Tag960() {
    initialize();
    postCreation();
  }

  public static Tag960 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag960();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "960";
    label = "Organizátor akce (pouze pro konverzi)";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");
    
    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "b", "Organizátor, role v nekódovaném tvaru", "NO",
      "n", "Název organizátora", "NO",
      "m", "Sídlo organizátora, místo (obec)", "NO",
      "z", "Sídlo organizátora, země", "NO"
    );
  }
}
