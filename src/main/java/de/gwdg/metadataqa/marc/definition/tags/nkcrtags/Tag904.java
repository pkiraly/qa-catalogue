package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag904 extends DataFieldDefinition {

  private static Tag904 uniqueInstance;

  private Tag904() {
    initialize();
    postCreation();
  }

  public static Tag904 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag904();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "904";
    label = "Všeobecné údaje – staré tisky";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "kódované údaje", "NR"
    );
  }
}
