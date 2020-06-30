package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag930 extends DataFieldDefinition {

  private static Tag930 uniqueInstance;

  private Tag930() {
    initialize();
    postCreation();
  }

  public static Tag930 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag930();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "930";
    label = "Práva k popisovanému zdroji";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Práva k popisovanému zdroji", "NR"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
