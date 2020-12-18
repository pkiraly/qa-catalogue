package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

/**
 * LDO (Legal Deposit Office) Information
 */
public class TagLDO extends DataFieldDefinition {

  private static TagLDO uniqueInstance;

  private TagLDO() {
    initialize();
    postCreation();
  }

  public static TagLDO getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagLDO();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "LDO";
    label = "LDO (Legal Deposit Office) Information";
    mqTag = "LegalDepositOfficeInformation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Source of record", "NR",
      "b", "Format of item used for record", "NR",
      "c", "Date at Copyright Agent", "NR",
      "d", "ALDL control number", "NR"
    );

    getSubfield("a").setMqTag("source");
    getSubfield("b").setMqTag("dormat");
    getSubfield("c").setMqTag("date");
    getSubfield("d").setMqTag("controlNumber");
  }
}
