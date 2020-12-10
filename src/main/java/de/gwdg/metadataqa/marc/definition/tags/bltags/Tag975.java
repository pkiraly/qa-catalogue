package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Insufficient Record Statement
 */
public class Tag975 extends DataFieldDefinition {

  private static Tag975 uniqueInstance;

  private Tag975() {
    initialize();
    postCreation();
  }

  public static Tag975 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag975();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "975";
    label = "Insufficient Record Statement";
    mqTag = "insufficientRecordStatement";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Indication of insufficient record", "NR",
      "b", "Indication of insufficient record - Oriental Romanised records", "NR"
    );

    getSubfield("a")
      .setCodes(
        "A", "Accents and modifications",
        "C", "Cyrillic",
        "d", "Deficient record",
        "F", "Filing element",
        "G", "Greek",
        "M", "Mathematical characters",
        "P", "Punctuation",
        "S", "Symbols",
        "T", "Transliterated characters",
        "X", "Superscript characters"
      )
      .setMqTag("insufficientRecord");

    getSubfield("b")
      .setCodes(
        "A", "Arabic",
        "C", "Chinese",
        "H", "Hebrew",
        "J", "Japanese",
        "K", "Korean",
        "P", "Persian",
        "Y", "Yiddish"
      )
      .setMqTag("orientalRomanised");
  }
}
