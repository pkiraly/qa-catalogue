package de.gwdg.metadataqa.marc.definition.tags.kbrtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Item information
 */
public class Tag997 extends DataFieldDefinition {

  private static Tag997 uniqueInstance;

  private Tag997() {
    initialize();
    postCreation();
  }

  public static Tag997 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag997();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "997";
    label = "Item information";
    mqTag = "ItemInformation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Barcode number", "NR", // (number)
      "c", "Library (holding institution)", "NR", // (text, mostly 'KBR')
      "d", "Section (department of holding institution)", "NR", // (text, like 'M-MAN' for (manuscripts))
      "g", "Call Number", "NR", // (text)
      "h", "Rating2", "NR", // (text)
      "i", "Rating3", "NR", // (text)
      "t", "Document type", "NR", // (text)
      "*", "Link with identifier", "NR", // (number)
      "@", "Language of field", "NR",
      "#", "number/occurrence of field", "NR" // (number)
    );

    getSubfield("a").setMqTag("barcode");
    getSubfield("c").setMqTag("library");
    getSubfield("d").setMqTag("section");
    getSubfield("g").setMqTag("callNumber");
    getSubfield("h").setMqTag("rating2");
    getSubfield("i").setMqTag("rating3");
    getSubfield("t").setMqTag("documentType");
    getSubfield("*").setMqTag("link");
    getSubfield("@").setMqTag("language");
    getSubfield("#").setMqTag("number");
  }
}
