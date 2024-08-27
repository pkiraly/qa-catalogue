package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *  Location (H52) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd852.html
 */
public class TagH52 extends DataFieldDefinition {

  private static TagH52 uniqueInstance;

  private TagH52() {
    initialize();
    postCreation();
  }

  public static TagH52 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH52();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H52";
    label = "Location (H52) - Marc 852";
    mqTag = "H52Location";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator("Shelving scheme")
    .setCodes(
      " ", "No information provided",
      "0", "Library of Congress classification",
      "1", "Dewey Decimal classification",
      "2", "National Library of Medicine classification",
      "3", "Superintendent of Documents classification",
      "4", "Shelving control number",
      "5", "Title",
      "6", "Shelved separately",
      "7", "Source specified in subfield $2",
      "8", "Other scheme"
    )
    .setMqTag("shelvingScheme");


    ind2 = new Indicator("Shelving order")
    .setCodes(
      " ", "No information provided",
      "0", "Not enumeration",
      "1", "Primary enumeration",
      "2", "Alternative enumeration"
    )
    .setMqTag("shelvingOrder");

    setSubfieldsWithCardinality(

// Location
    "a", "Location", "NR",
    "b", "Sublocation or collection", "R",
    "c", "Shelving location", "R",
    "d", "Former shelving location", "R",
    "e", "Address", "R",
    "f", "Coded location qualifier", "R",
    "g", "Non-coded location qualifier", "R",
    "u", "Uniform Resource Identifier", "R",
// Shelving designation
    "h", "Classification part", "NR",
    "i", "Item part", "R",
    "j", "Shelving control number", "NR",
    "k", "Call number prefix", "R",
    "l", "Shelving form of title", "NR",
    "m", "Call number suffix", "R",
// Numbers and codes
    "n", "Country code", "NR",
    "s", "Copyright article-fee code", "R",
    "t", "Copy number", "NR",
// Descriptors
    "p", "Piece designation", "NR",
    "q", "Piece physical condition", "NR",
// Notes
    "x", "Nonpublic note", "R",
    "z", "Public note", "R",
// Control subfields
    "2", "Source of classification or shelving scheme", "NR",
    "3", "Materials specified", "NR",
    "6", "Linkage", "NR",
    "8", "Sequence number", "NR"
     );
  }
}
