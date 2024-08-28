package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Inhaltsverzeichnis URL für Primo
 */
public class Tag965 extends DataFieldDefinition {

  private static Tag965 uniqueInstance;

  private Tag965() {
    initialize();
    postCreation();
  }

  public static Tag965 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag965();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "965";
    label = "Inhaltsverzeichnis URL für Primo";
    mqTag = "inhaltsverzeichnisURLFürPrimo";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=724304028";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "u", "Inhaltsverzeichnis URL für Primo", "R"
    );

  }
}
