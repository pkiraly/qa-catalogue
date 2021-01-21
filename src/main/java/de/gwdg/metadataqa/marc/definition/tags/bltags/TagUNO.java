package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Document Supply ETOC (Electronic Table of Contents) Flag
 */
public class TagUNO extends DataFieldDefinition {

  private static TagUNO uniqueInstance;

  private TagUNO() {
    initialize();
    postCreation();
  }

  public static TagUNO getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagUNO();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "UNO";
    label = "Unencrypted Download ID";
    mqTag = "UnencryptedDownloadID";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Code", "NR"
    );

    getSubfield("a")
      .setCodes(
        "Y", "Unencrypted download is allowed"
      ).setMqTag("code");
  }
}
