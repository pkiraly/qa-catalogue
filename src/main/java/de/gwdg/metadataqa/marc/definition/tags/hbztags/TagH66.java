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

    ind1 = new Indicator("Field encoding level")
    .setCodes(
      " ", "No information provided",
      "3", "Holdings level 3",
      "4", "Holdings level 4",
      "5", "Holdings level 4 with piece designation"
    )
    .setMqTag("FieldEncodingLevel");


    ind2 = new Indicator("Type of notation")
    .setCodes(
      "0", "Non-standard",
      "1", "ANSI/NISO Z39.71 or ISO 10324",
      "2", "ANSI Z39.42",
      "7", "Source specified in subfield $2 "
    )

    .setMqTag("TypeOfNotation");

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
