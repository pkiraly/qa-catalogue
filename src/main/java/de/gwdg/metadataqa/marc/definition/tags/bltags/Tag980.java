package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.ClassificationSchemeSourceCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd1OrIf7FromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;

/**
 * Card Production Indicator
 */
public class Tag980 extends DataFieldDefinition {

  private static Tag980 uniqueInstance;

  private Tag980() {
    initialize();
    postCreation();
  }

  public static Tag980 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag980();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "980";
    label = "Card Production Indicator";
    mqTag = "cardProductionIndicator";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Card production indicator", "NR"
    );

    getSubfield("a")
      .setCodes("Card", "Card")
      .setMqTag("cardProduction");
  }
}
