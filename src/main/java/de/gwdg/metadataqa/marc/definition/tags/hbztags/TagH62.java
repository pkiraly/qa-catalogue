package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Copy and Version Identification Note (H62) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd562.html
 */
public class TagH62 extends DataFieldDefinition {

  private static TagH62 uniqueInstance;

  private TagH62() {
    initialize();
    postCreation();
  }

  public static TagH62 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH62();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H62";
    label = "Copy and Version Identification Note (H62) - Marc 562";
    mqTag = "H62CopyandVersionIdentificationNote";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Identifying markings", "R",
      "b", "Copy identification", "R",
      "c", "Version identification", "R",
      "d", "Presentation format", "R",
      "e", "Number of copies", "R", 
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
  }
}
