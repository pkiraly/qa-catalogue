package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag900 extends DataFieldDefinition {

  private static Tag900 uniqueInstance;

  private Tag900() {
    initialize();
    postCreation();
  }

  public static Tag900 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag900();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "900";
    label = "Vlastník a úroveň zpracování záznamu";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Sigla vlastníka záznamu (zasílající instituce)", "NR",
      "b", "Váha záznamu", "NR"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
    getSubfield("b")
      .setCompilanceLevels("M");
  }
}
