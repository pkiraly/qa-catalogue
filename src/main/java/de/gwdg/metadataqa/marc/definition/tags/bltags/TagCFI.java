package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Copyright Fee Information
 */
public class TagCFI extends DataFieldDefinition {

  private static TagCFI uniqueInstance;

  private TagCFI() {
    initialize();
    postCreation();
  }

  public static TagCFI getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagCFI();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "CFI";
    label = "Copyright Fee Information";
    mqTag = "copyrightFeeInformation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator("Fee")
      .setCodes(
        "0", "Current fee",
        "1", "Pending fee",
        "2", "Historical fee"
      )
      .setMqTag("fee");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Fee and currency", "NR",
      "b", "Copyright licensing agency or publisher name etc.", "NR",
      "c", "Licence number", "NR",
      "d", "Authorised users", "NR",
      "e", "Commencement date", "NR",
      "f", "Article rate (Base rate)", "NR",
      "g", "Page rate", "NR",
      "h", "Fee source", "NR",
      "i", "Copyright excluded", "NR",
      "u", "Uniform Resource Identifier", "NR",
      "3", "Materials specified", "NR",
      "5", "Institution to which field applies", "NR",
      "6", "Linkage", "NR",
      "8", "Field link and sequence number", "R"
    );

    getSubfield("a").setMqTag("fee");
    getSubfield("b").setMqTag("copyright");
    getSubfield("c").setMqTag("licenceNumber");
    getSubfield("d").setMqTag("authorisedUsers");
    getSubfield("e").setMqTag("commencementDate");
    getSubfield("f").setMqTag("articleRate");
    getSubfield("g").setMqTag("pageRate");
    getSubfield("h")
      .setCodes(
        "A", "Average",
        "BL", "British Library",
        "C", "CLA (Copyright Licensing Agency)",
        "P", "Publisher",
        "Z", "Zero"
      )
      .setMqTag("feeSource");
    // TODO: check PDF - codes?
    getSubfield("i").setMqTag("copyrightExcluded");
    getSubfield("u").setMqTag("uri");
    getSubfield("3").setMqTag("materialsSpecified");
    getSubfield("5")
      .setCodes(
        "Uk", "Uk"
      )
      .setMqTag("institutionToWhichFieldApplies");
    getSubfield("6").setMqTag("linkage");
    getSubfield("8").setMqTag("fieldLink");

  }
}
