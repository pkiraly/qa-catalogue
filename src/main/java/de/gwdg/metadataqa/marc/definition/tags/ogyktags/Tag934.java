package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag934 extends DataFieldDefinition {

  private static Tag934 uniqueInstance;

  private Tag934() {
    initialize();
    postCreation();
  }

  public static Tag934 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag934();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "934";
    label = "Szolgáltatás kódja";
    mqTag = "SzolgaltatasKodja";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:933-934";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Kód", "NR"
    );
  }
}
