package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag597 extends DataFieldDefinition {

  private static Tag597 uniqueInstance;

  private Tag597() {
    initialize();
    postCreation();
  }

  public static Tag597 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag597();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "597";
    label = "Megjegyzés a megjelenésről";
    mqTag = "MegjegyzesMegjelenesrol";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:597";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Megjegyzés szövege", "NR"
    );
  }
}
