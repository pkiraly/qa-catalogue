package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Textual Holdings - Basic Bibliographic Unit (H66) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd866.html
 */
public class TagH66 extends DataFieldDefinition {

  private static TagH66 uniqueInstance;

  private TagH66() {
    initialize();
    postCreation();
  }

  public static TagH66 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH66();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H66";
    label = "Textual Holdings - Basic Bibliographic Unit (H66) - Marc 866";
    mqTag = "H66ElectronicLocationandAccess";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Textual holdings", "NR",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R", 
      "2", "Source of notation", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
  }
}
