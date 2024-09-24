package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag997 extends DataFieldDefinition {

  private static Tag997 uniqueInstance;

  private Tag997() {
    initialize();
    postCreation();
  }

  public static Tag997 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag997();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "997";
    label = "Digitalizálás (kronologikus besorolás gyűjteménybe szervezéshez)";
    mqTag = "DGY2";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:996-997";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Kód", "NR",
      "b", "Kód feloldása", "NR"
    );
  }
}
