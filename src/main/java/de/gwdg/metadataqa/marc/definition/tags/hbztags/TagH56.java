package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Electronic Location and Access (H56) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd856.html
 */
public class TagH56 extends DataFieldDefinition {

  private static TagH56 uniqueInstance;

  private TagH56() {
    initialize();
    postCreation();
  }

  public static TagH56 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH56();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H56";
    label = "Electronic Location and Access (H56) - Marc 856";
    mqTag = "H56ElectronicLocationandAccess";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(

    "a", "Host name", "R",
    "c", "Compression information", "R",
    "d", "Path", "R",
    "f", "Electronic name", "R",
    "g", "Persistent identifier", "R",
    "h", "Non-functioning Uniform Resource Identifier", "R",
    "l", "Standardized information governing access", "R",
    "m", "Contact for access assistance", "R",
    "n", "Terms governing access", "R",
    "o", "Operating system", "NR",
    "p", "Port", "NR",
    "q", "Electronic format type", "R",
    "r", "Standardized information governing use and reproduction", "R",
    "s", "File size", "R",
    "t", "Terms governing use and reproduction", "R",
    "u", "Uniform Resource Identifier", "R",
    "v", "Hours access method available", "R",
    "w", "Record control number", "R",
    "x", "Nonpublic note", "R",
    "y", "Link text", "R",
    "z", "Public note", "R",
    "2", "Access method", "NR",
    "3", "Materials specified", "NR",
    "6", "Linkage", "NR",
    "7", "Access status", "NR",
    "8", "Field link and sequence number", "R"

    );
  }
}
