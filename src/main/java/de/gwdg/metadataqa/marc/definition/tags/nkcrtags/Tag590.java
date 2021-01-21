package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag590 extends DataFieldDefinition {

  private static Tag590 uniqueInstance;

  private Tag590() {
    initialize();
    postCreation();
  }

  public static Tag590 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag590();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "590";
    label = "Lokální poznámka";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://text.nkp.cz/o-knihovne/odborne-cinnosti/zpracovani-fondu/roztridit/stt-marc-minzazn";
    setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "text poznámky", "NR",
      "5", "kód instituce MARC", "NR"
    );

    getSubfield("a")
      .setCompilanceLevels("M");
    getSubfield("5")
      .setCompilanceLevels("A");
  }
}
