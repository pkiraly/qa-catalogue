package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag596 extends DataFieldDefinition {

  private static Tag596 uniqueInstance;

  private Tag596() {
    initialize();
    postCreation();
  }

  public static Tag596 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag596();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "596";
    label = "Megjegyzés a szabványosításról, az adatgyűjtés lezárásáról és a hatályba lépésről";
    mqTag = "MegjegyzesSzabvanyositasiAdatok";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:596";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Megjegyzés szövege", "NR"
    );
  }
}
