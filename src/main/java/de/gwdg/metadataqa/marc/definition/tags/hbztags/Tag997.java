package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Aufnahmedatum, Aufnahmejahr
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
    label = "Aufnahmedatum, Aufnahmejahr";
    mqTag = "AufnahmedatumAufnahmejahr";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Aufnahmedatum, Aufnahmejahr", "R",
      "9", "Verweis auf Lokalinformation","NR"    
    );

    //TODO: Field a needs to be YYYYMMDD

  }
}
