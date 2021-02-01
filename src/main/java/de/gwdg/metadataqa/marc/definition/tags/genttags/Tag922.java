package de.gwdg.metadataqa.marc.definition.tags.genttags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * classifying grey literature
 */
public class Tag922 extends DataFieldDefinition {

  private static Tag922 uniqueInstance;

  private Tag922() {
    initialize();
    postCreation();
  }

  public static Tag922 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag922();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "922";
    label = "Grey literature classification";
    mqTag = "Grey literature classification";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Titel der Unterreihe", "NR",
      "d", "Zusatz zum Sachtitel/Parallelsachtitel der Unterreihe", "R",
      "e", "Reihenbezeichnung und /oder Zählung", "R",
      "f", "Parallelsachtitel zur UR(@)", "R",
      "h", "Verfasserangabe zur Unterreihe", "NR",
      "l", "Reihenbezeichnung und /oder Zählung", "NR",
      "n", "Reihenbezeichnung und /oder Zählung", "NR",
      "r", "Undifferenzierter Text", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
