package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Acquisitions Data
 */
public class Tag957 extends DataFieldDefinition {

  private static Tag957 uniqueInstance;

  private Tag957() {
    initialize();
    postCreation();
  }

  public static Tag957 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag957();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "957";
    label = "Acquisitions Data";
    mqTag = "Acquisitions";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Receipt date and method of acquisition code or Cataloguer initials", "R",
      "b", "Receipt date or Name of supplier (Asia, Pacific & Africa Collections)", "R",
      "c", "Method of acquisition code", "R",
      "d", "Cataloguer initials - reviser", "R",
      "r", "Receipt date", "NR",
      "s", "British Museum stamp number", "R",
      "t", "Legal Deposit Office batch number", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^((\\d{4}|\\d{6}|\\d{8})?(c|CR|d|w|p)?|[A-Z]+)$"))
      .setMqTag("acquisition");
    // TODO
    /*
    getSubfield("a")
      .setCodes(
        "c", "Legal Deposit",
        "CR", "Copyright",
        "d", "Donation",
        "e", "Exchange",
        "p", "Purchase"
      )
    */

    getSubfield("b").setMqTag("supplier");
    getSubfield("c").setMqTag("method");
    getSubfield("d").setMqTag("cataloguer");
    getSubfield("r").setMqTag("receiptDate");

    getSubfield("s")
      .setCodes(
        "1", "ca.1750-1834",
        "2", "ca.1830s",
        "3", "ca.1811-1834",
        "4", "1826-1835",
        "5", "Special collections: Clayton Mordaunt, Cracherode",
        "6", "Special collections: Frances Hargrave",
        "7", "1837-1850",
        "8", "1850-1870",
        "9", "1870-1912 (copyright), 1870-1923 (donation), 1870-1925 (purchased)",
        "10", "1912-1929 (copyright), 1923-1929 (donation), 1925-1929 (purchased)",
        "11", "1912-1929 (copyright), 1923-1929 (donation), 1925-1929 (purchased)",
        "12", "1929-1972",
        "13", "Special collections: The Grenville Library",
        "14a", "George III Kings Library",
        "14", "Ashley Library",
        "14b", "Ashley Library",
        "15", "1973-",
        "16", "1973-"
      )
      .setMqTag("stampNumber");

    getSubfield("t").setMqTag("batch");
  }
}
