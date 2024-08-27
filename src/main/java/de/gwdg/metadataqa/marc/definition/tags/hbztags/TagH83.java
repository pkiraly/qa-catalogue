package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Copy and Version Identification Note (H83) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd583.html
 */
public class TagH83 extends DataFieldDefinition {

  private static TagH83 uniqueInstance;

  private TagH83() {
    initialize();
    postCreation();
  }

  public static TagH83 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH83();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H83";
    label = "Action Note (H83) - Marc 583";
    mqTag = "H83ActionNote";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator("Privacy")
    .setCodes(
      " ", "No information provided",
      "0", "Private",
      "1", "Not Private"
    )
    .setMqTag("privacy");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Action", "NR",
      "b", "Action identification", "R",
      "c", "Time/date of action", "R",
      "d", "Action interval", "R",
      "e", "Contingency for action", "R",
      "f", "Authorization", "R",
      "h", "Jurisdiction", "R",
      "i", "Method of action", "R",
      "j", "Site of action", "R",
      "k", "Action agent", "R",
      "l", "Status", "R", 
      "n", "Extent", "R",
      "o", "Type of unit", "R",
      "u", "Uniform Resource Identifier", "R",
      "x", "Nonpublic note", "R",
      "z", "Public note", "R",
      "2", "Source of term", "NR",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );
  }
}
