package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	National Bibliographic Agency Control Number (H16) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd016.html
 */
public class TagH16 extends DataFieldDefinition {

  private static TagH16 uniqueInstance;

  private TagH16() {
    initialize();
    postCreation();
  }

  public static TagH16 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH16();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H16";
    label = "National Bibliographic Agency Control Number (H16) - Marc 016 024";
    mqTag = "H16NationalBibliographicAgencyControlNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator("National bibliographic agency")
    .setCodes(
      " ", "Library and Archives Canada",
      "7", "Source specified in subfield $2"
    )
    .setMqTag("nationalBibliographicAgency");
    
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Record control number", "NR",
      "z", "Canceled or invalid control number", "R", 
      "2", "Source", "NR",
      "8", "Field link and sequence number", "R"
    );
  }
}
