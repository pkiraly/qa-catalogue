package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Lokales Schlagwort
 */
public class Tag982 extends DataFieldDefinition {

  private static Tag982 uniqueInstance;

  private Tag982() {
    initialize();
    postCreation();
  }

  public static Tag982 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag982();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "982";
    label = "Lokales Schlagwort";
    mqTag = "LokalesSchlagwort";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Schlagwort, unaufgegliedert", "R",
      "b", "Sachschlagwort", "R",
      "c", "Formschlagwort", "R",
      "9", "Verweis auf Lokalinformation","NR"          
    );

  }
}
