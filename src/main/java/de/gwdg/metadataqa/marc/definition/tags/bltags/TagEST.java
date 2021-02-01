package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Document Supply ESTAR (Electronic Storage and Retrieval System) Flag
 */
public class TagEST extends DataFieldDefinition {

  private static TagEST uniqueInstance;

  private TagEST() {
    initialize();
    postCreation();
  }

  public static TagEST getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagEST();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "EST";
    label = "Document Supply ESTAR (Electronic Storage and Retrieval System) Flag";
    mqTag = "EstarFlag";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "ESTAR flag", "NR"
    );

    getSubfield("a")
      .setCodes(
        "1", "Electronic ESTAR",
        "2", "Print"
      )
      .setMqTag("estarFlag");
  }
}
