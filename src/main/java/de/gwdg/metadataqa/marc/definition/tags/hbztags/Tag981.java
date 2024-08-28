package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Systemnummer alt
 */
public class Tag981 extends DataFieldDefinition {

  private static Tag981 uniqueInstance;

  private Tag981() {
    initialize();
    postCreation();
  }

  public static Tag981 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag981();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "981";
    label = "Systemnummer alt";
    mqTag = "systemnummerAlt";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Systemnummer alt", "NR",
      "u", " IDN Exemplar (EPN)", "NR"      
    );

  }
}
