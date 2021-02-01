package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag905 extends DataFieldDefinition {

  private static Tag905 uniqueInstance;

  private Tag905() {
    initialize();
    postCreation();
  }

  public static Tag905 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag905();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "905";
    label = "Všeobecné údaje – staré tisky";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "kódované údaje", "NR",
      "5", "kód instituce", "NR"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
    getSubfield("5")
      .setCompilanceLevels("A");
  }
}
