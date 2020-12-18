package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Newspaper Identifier
 */
public class TagNID extends DataFieldDefinition {

  private static TagNID uniqueInstance;

  private TagNID() {
    initialize();
    postCreation();
  }

  public static TagNID getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagNID();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "NID";
    label = "Newspaper Identifier";
    mqTag = "NewspaperIdentifier";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Identifier", "NR"
    );

    getSubfield("a")
      .setMqTag("nidIdentifier");
  }
}
