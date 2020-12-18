package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.ISSNValidator;

/**
 * Medium-Neutral Identifier
 */
public class TagMNI extends DataFieldDefinition {

  private static TagMNI uniqueInstance;

  private TagMNI() {
    initialize();
    postCreation();
  }

  public static TagMNI getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagMNI();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "MNI";
    label = "Medium-Neutral Identifier";
    mqTag = "MediumNeutralIdentifier";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Medium-neutral identifier", "NR"
    );

    getSubfield("a")
      .setValidator(ISSNValidator.getInstance())
      .setMqTag("mediumNeutralIdentifier");
  }
}
