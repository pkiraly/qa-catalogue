package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag998 extends DataFieldDefinition {

  private static Tag998 uniqueInstance;

  private Tag998() {
    initialize();
    postCreation();
  }

  public static Tag998 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag998();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "998";
    label = "Digitalizálás (tematikus besorolás gyűjteménybe szervezéshez)";
    mqTag = "DGY1";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:998";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Kód", "NR",
      "b", "Kód feloldása", "NR"
    );

    getSubfield("a").setMqTag("Kod");
    getSubfield("b").setMqTag("KodFeloldasa");

  }
}
