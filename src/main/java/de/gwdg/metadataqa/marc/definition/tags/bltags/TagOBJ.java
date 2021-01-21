package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Digital Object Field
 */
public class TagOBJ extends DataFieldDefinition {

  private static TagOBJ uniqueInstance;

  private TagOBJ() {
    initialize();
    postCreation();
  }

  public static TagOBJ getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagOBJ();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "OBJ";
    label = "Digital Object Field";
    mqTag = "DigitalObjectField";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Code", "NR"
    );

    getSubfield("a")
      .setCodes("Y", "Y")
      .setMqTag("code");
  }
}
