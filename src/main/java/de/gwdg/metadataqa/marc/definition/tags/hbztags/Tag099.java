package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Datum für den Austauschsatz
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686
 */
public class Tag099 extends DataFieldDefinition {

  private static Tag099 uniqueInstance;

  private Tag099() {
    initialize();
    postCreation();
  }

  public static Tag099 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag099();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "099";
    label = "Datum für den Austauschsatz";
    mqTag = "DatumFürDenAustauschsatz";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Kennzeichnungen für Nationallizenzen und digitale Sammlungen", "NR",
      "b", "Lizenzjahr", "NR"
    );
  }
}
