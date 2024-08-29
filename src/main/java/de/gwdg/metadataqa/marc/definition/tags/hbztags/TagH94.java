package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Lokale Notation - LOCAL 094 (H94) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 * https://www.alma-dach.org/alma-marc/holdings/094/094.html
 */
public class TagH94 extends DataFieldDefinition {

  private static TagH94 uniqueInstance;

  private TagH94() {
    initialize();
    postCreation();
  }

  public static TagH94 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH94();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H94";
    label = "LOCAL 094 (H94)";
    mqTag = "LOCAL094";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();


    setSubfieldsWithCardinality(
       "a", "Notation", "R",
      "8", "ALMA MMS ID linking HOL to HXX elements"
       );
  }
}
