package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag599 extends DataFieldDefinition {

  private static Tag599 uniqueInstance;

  private Tag599() {
    initialize();
    postCreation();
  }

  public static Tag599 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag599();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "599";
    label = "Példány- és verzióazonosító megjegyzés";
    mqTag = "PeldanyMegjegyzes";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:599";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Megjegyzés szövege", "NR"
    );
  }
}
