package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.RegexValidator;

/**
 * Document Supply Bibliographic History Note
 */
public class Tag595 extends DataFieldDefinition {

  private static Tag595 uniqueInstance;

  private Tag595() {
    initialize();
    postCreation();
  }

  public static Tag595 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag595();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "595";
    label = "Document Supply Bibliographic History Note";
    mqTag = "DocumentSupplyBibliographicHistoryNote";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Bibliographic history note", "NR"
    );

    getSubfield("a")
      .setValidator(new RegexValidator("^.*\\.$"))
      .setMqTag("note");
  }
}
