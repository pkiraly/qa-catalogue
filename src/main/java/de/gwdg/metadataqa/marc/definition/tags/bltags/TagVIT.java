package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RangeValidator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Virtual Item
 */
public class TagVIT extends DataFieldDefinition {

  private static TagVIT uniqueInstance;

  private TagVIT() {
    initialize();
    postCreation();
  }

  public static TagVIT getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagVIT();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "VIT";
    label = "Virtual Item";
    mqTag = "VirtualItem";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "b", "The Barcode Identifier allocated to the Primary Item", "NR",
      "c", "Condition/Surrogacy Status", "NR",
      "d", "Item Description", "NR",
      "e", "From Year", "NR",
      "f", "From Month", "NR",
      "g", "From Day", "NR",
      "i", "From Year (and To Year if different)", "NR",
      "j", "From Month (and To Month if different)", "NR",
      "k", "From Day (and To Day if different)", "NR",
      "o", "OPAC Note", "NR",
      "s", "Contains the string ‘Supplement’ if the Newspaper Audit database Item Description field contained ‘supp’. Otherwise blank", "NR"
    );

    getSubfield("b")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("barcode");
    getSubfield("c")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("status");
    getSubfield("d")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("description");
    getSubfield("e")
      .setValidator(new RegexValidator("^(1[5-9]|20)\\d\\d$"))
      .setMqTag("fromYear");
    getSubfield("f")
      .setValidator(new RangeValidator(1, 12))
      .setMqTag("fromMonth");
    getSubfield("g")
      .setValidator(new RangeValidator(1, 31))
      .setMqTag("fromDay");
    getSubfield("i")
      .setValidator(new RegexValidator("^(1[5-9]|20)\\d\\d(/(1[6-9]|20)\\d\\d)?$"))
      .setMqTag("fromToYear");
    getSubfield("j")
      .setValidator(new RegexValidator("^(0[1-9]|1[0-2])(/(0[1-9]|1[0-2]))?$"))
      .setMqTag("fromToMonth");
    getSubfield("k")
      .setValidator(
        new RegexValidator("^(0[1-9]|[1-2]\\d|3[01])(/(0[1-9]|[1-2]\\d|3[01]))?$"))
      .setMqTag("fromToDay");
    getSubfield("o")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("opacNote");
    // TODO: in the PDF it is not well formatted
    getSubfield("s")
      .setValidator(new RegexValidator("^(Supplement|)$"))
      .setMqTag("isSupplement");
  }
}
