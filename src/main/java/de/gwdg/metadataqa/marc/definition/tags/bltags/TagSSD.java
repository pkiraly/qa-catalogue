package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * STM Serials Designation
 */
public class TagSSD extends DataFieldDefinition {

  private static TagSSD uniqueInstance;

  private TagSSD() {
    initialize();
    postCreation();
  }

  public static TagSSD getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagSSD();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "SSD";
    label = "STM Serials Designation";
    mqTag = "STMSerialsDesignation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");
    obsolete = true;

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "STM serials designation and linking data", "NR"
    );

    getSubfield("a").setMqTag("stmSerialsDesignation");
  }
}
