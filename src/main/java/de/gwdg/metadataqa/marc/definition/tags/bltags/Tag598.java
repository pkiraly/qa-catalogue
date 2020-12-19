package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Document Supply Selection / Ordering Information
 */
public class Tag598 extends DataFieldDefinition {

  private static Tag598 uniqueInstance;

  private Tag598() {
    initialize();
    postCreation();
  }

  public static Tag598 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag598();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "598";
    label = "Document Supply Selection / Ordering Information";
    mqTag = "DocumentSupplySelectionOrderingInformation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Document Supply selection/ordering information", "NR"
    );

    getSubfield("a")
      .setMqTag("documentSupplySelection");
  }
}
