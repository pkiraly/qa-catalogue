package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#933
 */
public class Tag933 extends DataFieldDefinition {

  private static Tag933 uniqueInstance;

  private Tag933() {
    initialize();
    postCreation();
  }

  public static Tag933 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag933();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "933";
    label = "KOMITEAN SIHTEERI - VOYAGER-KENTTÄ";
    mqTag = "KomiteanSihteeri";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#933";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Komitean sihteeri: sukunimi, etunimi", "NR",
      "c", "Muut lisäykset", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
