package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Portfolio Embargo Information (POE) from ALMA Publishing
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagPOE extends DataFieldDefinition {

  private static TagPOE uniqueInstance;

  private TagPOE() {
    initialize();
    postCreation();
  }

  public static TagPOE getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagPOE();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "POE";
    label = "Portfolio Embargo Information (POE)";
    mqTag = "PortfolioEmbargoInformation";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality("a", "Portfolio ID subfield", "R",
      "b", "Operator subfield", "R",
      "c", "Number of Years subfield", "R",
      "d", "From Month subfield", "R",
      "e", "Number of Months subfield", "R"

  );
  }
}
