package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Monograph in Series Flag
 */
public class TagMIS extends DataFieldDefinition {

  private static TagMIS uniqueInstance;

  private TagMIS() {
    initialize();
    postCreation();
  }

  public static TagMIS getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagMIS();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "MIS";
    label = "Monograph in Series Flag";
    mqTag = "MonographInSeriesFlag";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Monograph in series flag", "NR"
    );

    getSubfield("a")
      .setCodes("BS", "Book Series")
      .setMqTag("monographInSeries");
    // TODO:
    //  obsolte subfield name: Monograph in series order type
    //  obsolte codes: "SO", "Standing order", "SUB", "Subscription"
    //  obsolte subfield name: Monograph in series flag
    //  obsolte codes: "k-sep", "k-sep"
  }
}
