package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Stored Search Flag
 */
public class Tag992 extends DataFieldDefinition {

  private static Tag992 uniqueInstance;

  private Tag992() {
    initialize();
    postCreation();
  }

  public static Tag992 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag992();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "992";
    label = "Stored Search Flag";
    mqTag = "StoredSearchFlag";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Stored search code", "R"
    );

    getSubfield("a")
      .setCodes(
        "C01", "Science Collection",
        "C02", "Humanities Collection",
        "C03", "All Document Supply",
        "C04", "Document Supply monographs",
        "C06", "Document Supply serials",
        "C07", "Current music",
        "C08", "All music",
        "C10", "All cartographic",
        "C11", "APAC (Asia, Pacific & Africa Collections) current",
        "C12", "All APAC (Asia, Pacific & Africa Collections)",
        "C13", "All serials",
        "C16", "UCB (Union Catalogue of Books)",
        "C20", "SPHOA (St Pancras Humanities Open Access Collection)"
      )
      .setMqTag("storedSearchCode");
  }
}
