package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Finished (Cataloguing)
 */
public class TagLAS extends DataFieldDefinition {

  private static TagLAS uniqueInstance;

  private TagLAS() {
    initialize();
    postCreation();
  }

  public static TagLAS getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagLAS();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "LAS";
    label = "Last CAT Field";
    mqTag = "LastCATField";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    // TODO: check BL Local Fields Manual - Aleph fields, field CAT (Cataloguer).
    setSubfieldsWithCardinality(
      "a", "Aleph user ID", "NR",
      "b", "Cataloguer level", "NR",
      "l", "Aleph library", "NR",
      "h", "Time", "NR",
      "c", "Date", "NR"
    );

    getSubfield("a").setMqTag("userID");
    getSubfield("b").setMqTag("cataloguerlevel");
    getSubfield("l").setMqTag("alephLibrary");
    getSubfield("h").setMqTag("time");
    getSubfield("c").setMqTag("date");
  }
}
