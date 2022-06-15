package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag968 extends DataFieldDefinition {

  private static Tag968 uniqueInstance;

  private Tag968() {
    initialize();
    postCreation();
  }

  public static Tag968 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag968();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "968";
    label = "Oborové třídění periodik Ministerstva kultury ČR";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/informativni-materialy/blok-9xxuni-m21";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Notace", "R"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
  }
}
