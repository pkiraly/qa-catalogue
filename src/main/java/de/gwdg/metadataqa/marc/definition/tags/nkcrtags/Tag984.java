package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag984 extends DataFieldDefinition {

  private static Tag984 uniqueInstance;

  private Tag984() {
    initialize();
    postCreation();
  }

  public static Tag984 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag984();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "984";
    label = "Město vydání (staré tisky)";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Město", "NR",
      "b", "Země", "NR",
      "c", "Stát, provincie", "NR",
      "d", "Kraj, okres", "NR"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
    getSubfield("b")
      .setCompilanceLevels("M");
  }
}
