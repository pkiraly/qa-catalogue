package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

public class Tag932 extends DataFieldDefinition {

  private static Tag932 uniqueInstance;

  private Tag932() {
    initialize();
    postCreation();
  }

  public static Tag932 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag932();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "932";
    label = "Akronym (oborové informační brány)";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("A", "A");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Akronym", "NR"
    );
  }
}
