package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag996 extends DataFieldDefinition {

  private static Tag996 uniqueInstance;

  private Tag996() {
    initialize();
    postCreation();
  }

  public static Tag996 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag996();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "996";
    label = "Digitalizálás (projekt-kód)";
    mqTag = "DDK";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:996-997";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Kód", "NR"
    );
  }
}
