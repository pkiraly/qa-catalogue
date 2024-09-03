package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Sonstige Standardnummer
 */
public class Tag961 extends DataFieldDefinition {

  private static Tag961 uniqueInstance;

  private Tag961() {
    initialize();
    postCreation();
  }

  public static Tag961 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag961();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "961";
    label = "Sonstige Standardnummer";
    mqTag = "SonstigeStandardnummer";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/nebenbei/cgi/anonymous_display_only.pl?pageId=717357250";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Sonstige Standardnummer", "R"
    );

  }
}
