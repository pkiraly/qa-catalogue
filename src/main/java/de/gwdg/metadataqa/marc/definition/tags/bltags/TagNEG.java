package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * LDO (Legal Deposit Office) Signoff
 */
public class TagNEG extends DataFieldDefinition {

  private static TagNEG uniqueInstance;

  private TagNEG() {
    initialize();
    postCreation();
  }

  public static TagNEG getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagNEG();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "NEG";
    label = "LDO (Legal Deposit Office) Signoff";
    mqTag = "LegalDepositOfficeSignoff";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "LDO signoff", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("ldoSignoff");
  }
}
