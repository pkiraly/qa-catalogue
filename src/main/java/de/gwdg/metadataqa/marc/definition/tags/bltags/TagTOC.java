package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Document Supply ETOC (Electronic Table of Contents) Flag
 */
public class TagTOC extends DataFieldDefinition {

  private static TagTOC uniqueInstance;

  private TagTOC() {
    initialize();
    postCreation();
  }

  public static TagTOC getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagTOC();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "TOC";
    label = "Document Supply ETOC (Electronic Table of Contents) Flag";
    mqTag = "ElectronicTableOfContentsFlag";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "ETOC flag", "NR"
    );

    getSubfield("a")
      .setCodes(
        "ETOC", "ETOC"
      ).setMqTag("etoc");
  }
}
