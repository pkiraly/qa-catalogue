package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Document Supply Conference Heading
 */
public class TagCNF extends DataFieldDefinition {

  private static TagCNF uniqueInstance;

  private TagCNF() {
    initialize();
    postCreation();
  }

  public static TagCNF getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagCNF();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "CNF";
    label = "Document Supply Conference Heading";
    mqTag = "DocumentSupplyConferenceHeading";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd037.html";
    // setCompilanceLevels("O");

    ind1 = new Indicator();

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Conference name or corporate name as entry element", "NR",
      "c", "Location of conference", "NR",
      "d", "Date of conference", "NR",
      "e", "Subordinate unit", "R",
      "n", "Number of conference", "NR"
    );

    getSubfield("a").setMqTag("name");
    getSubfield("c").setMqTag("location");
    getSubfield("d").setMqTag("date");
    getSubfield("e").setMqTag("subordinateUnit");
    getSubfield("n").setMqTag("numberOfConference");
  }
}
