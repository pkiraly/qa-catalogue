package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Other Standard Identifier (H24) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd024.html
 */
public class TagH24 extends DataFieldDefinition {

  private static TagH24 uniqueInstance;

  private TagH24() {
    initialize();
    postCreation();
  }

  public static TagH24 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH24();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H24";
    label = "Other Standard Identifier (H24) - Marc 024";
    mqTag = "H24OtherStandardIdentifier";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
    "a", "Standard number or code", "NR",
    "c", "Terms of availability", "NR",
    "d", "Additional codes following the standard number or code", "NR",
    "q", "Qualifying information", "R",
    "z", "Canceled/invalid standard number or code", "R",
    "2", "Source of number or code", "NR",
    "6", "Linkage", "NR",
    "8", "Field link and sequence number", "R"
    );
  }
}
