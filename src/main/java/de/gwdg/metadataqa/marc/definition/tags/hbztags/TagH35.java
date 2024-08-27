package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import org.apache.spark.internal.config.R;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	System Control Number (H35) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd035.html
 */
public class TagH35 extends DataFieldDefinition {

  private static TagH35 uniqueInstance;

  private TagH35() {
    initialize();
    postCreation();
  }

  public static TagH35 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH35();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H35";
    label = "System Control Number (H35) - Marc 035";
    mqTag = "H35SystemControlNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "System control number", "NR",
      "z", "Canceled or invalid control number", "R",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
  }
}
