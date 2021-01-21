package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Collection Subset
 */
public class Tag690 extends DataFieldDefinition {

  private static Tag690 uniqueInstance;

  private Tag690() {
    initialize();
    postCreation();
  }

  public static Tag690 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag690();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "690";
    label = "Collection Subset";
    mqTag = "CollectionSubset";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator("Provides information on the content of subfield $a")
      .setCodes(
        "7", "Source specified in subfield $2"
      )
      .setMqTag("source");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Collection subset", "NR",
      "2", "Source of term", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("collectionSubset");

    getSubfield("2")
      .setValidator(new RegexValidator("^.*[^\\.]$"))
      .setMqTag("source");
  }
}
