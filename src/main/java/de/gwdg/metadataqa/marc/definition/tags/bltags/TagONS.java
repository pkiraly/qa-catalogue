package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * ONIX Subjects
 */
public class TagONS extends DataFieldDefinition {

  private static TagONS uniqueInstance;

  private TagONS() {
    initialize();
    postCreation();
  }

  public static TagONS getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagONS();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "ONS";
    label = "ONIX Subjects";
    mqTag = "OnixSubjects";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator("Provides information on the content of subfield $a")
      .setCodes(
        " ", "No information provided",
        "7", "Source specified in subfield $2"
      )
      .setMqTag("contentOfSubfield");

    setSubfieldsWithCardinality(
      "a", "Subject code", "NR",
      "t", "Subject heading text", "NR",
      "x", "Remainder of BISAC subject code", "NR",
      "2", "Source of term", "NR"
    );

    getSubfield("a").setMqTag("code");
    getSubfield("t").setMqTag("text");
    getSubfield("x").setMqTag("bisac");
    getSubfield("2")
      .setCodes(
        "bisacsh", "BISAC subject code",
        "bicssc", "BIC subject code or BIC geographical qualifier, or BIC language qualifier, or BIC time period qualifier",
        "Subject Keywords", "subject keywords"
      )
      .setMqTag("source");
  }
}
