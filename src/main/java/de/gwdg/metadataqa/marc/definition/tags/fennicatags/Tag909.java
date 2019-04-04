package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#909
 */
public class Tag909 extends DataFieldDefinition {

  private static Tag909 uniqueInstance;

  private Tag909() {
    initialize();
    postCreation();
  }

  public static Tag909 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag909();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "909";
    label = "REPLIKOINTIKENTTÄ";
    mqTag = "Replikointikenttä";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#909";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Fennican luettelointikäytäntö", "NR",
      "b", "Replikoituvat kentät/kenttäryhmät", "NR",
      "c", "Aikaleima", "NR",
      "5", "Tietokantatunnus", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
