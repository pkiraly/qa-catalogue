package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag903 extends DataFieldDefinition {

  private static Tag903 uniqueInstance;

  private Tag903() {
    initialize();
    postCreation();
  }

  public static Tag903 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag903();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "903";
    label = "Označení - staré tisky";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Označení \"RP\"", "NR"
    );

    getSubfield("a")
      .setCodes(
      "RP", "Rair Print"
      );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
