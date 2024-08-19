package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Portfolio Coverage Information (POC) from ALMA Publishing
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagPOC extends DataFieldDefinition {

  private static TagPOC uniqueInstance;

  private TagPOC() {
    initialize();
    postCreation();
  }

  public static TagPOC getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagPOC();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "POC";
    label = "Portfolio Coverage Information (POC)";
    mqTag = "PortfolioCoverageInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality("a", "Portfolio ID subfield", "R",
      "b", "From Year subfield", "R",
      "c", "Until Year subfield", "R",
      "d", "From Month subfield", "R",
      "e", "Until Month subfield", "R",
      "f", "From Day subfield", "R",
      "g", "Until Day subfield", "R",
      "h", "From Volume subfield", "R",
      "i", "Until Volume subfield", "R",
      "j", "From Issue subfield", "R",
      "k", "Until Issue subfield", "R"

  );
  }
}
