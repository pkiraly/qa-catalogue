package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.general.validator.DateValidator;

/**
 * Finished (Cataloguing)
 */
public class TagFIN extends DataFieldDefinition {

  private static TagFIN uniqueInstance;

  private TagFIN() {
    initialize();
    postCreation();
  }

  public static TagFIN getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagFIN();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "FIN";
    label = "Finished (Cataloguing)";
    mqTag = "FinishedCataloguing";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Finished", "NR",
      "d", "Date", "NR"
    );

    getSubfield("a")
      .setCodes(
        "Y", "Y"
      )
      .setMqTag("finished");

    getSubfield("d")
      .setValidator(new DateValidator("yyyyMMdd"))
      .setMqTag("date");
  }
}
