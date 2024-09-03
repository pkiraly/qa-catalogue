package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	 	Reproduction Note (H43) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd843.html
 */
public class TagH43 extends DataFieldDefinition {

  private static TagH43 uniqueInstance;

  private TagH43() {
    initialize();
    postCreation();
  }

  public static TagH43 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH43();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H43";
    label = "Reproduction Note (H43) - Marc 843";
    mqTag = "H43ReproductionNote";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Type of reproduction", "NR",
      "b", "Place of reproduction", "R",
      "c", "Agency responsible for reproduction", "R",
      "d", "Date of reproduction", "NR",
      "e", "Physical description of reproduction", "NR",
      "f", "Series statement of reproduction", "R",
      "m", "Dates of publication and/or sequential designation of issues reproduced", "R",
      "n", "Note about reproduction", "R",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR", 
      "7", "Fixed-length data elements of reproduction", "NR",
      "8", "Field link and sequence number", "R"
    );
  }
}
