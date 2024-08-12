package de.gwdg.metadataqa.marc.definition.tags.ogyktags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Locally defined field in OGYK
 */
public class Tag592 extends DataFieldDefinition {

  private static Tag592 uniqueInstance;

  private Tag592() {
    initialize();
    postCreation();
  }

  public static Tag592 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag592();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "592";
    label = "Megjegyzés a különlenyomatról";
    mqTag = "MegjegyzesKulonlenyomatrol";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://wiki.ogyk.hu/doku.php?id=ogyk:gyszo:katszab:konyv:592";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Megjegyzés szövege", "NR"
    );
  }
}
