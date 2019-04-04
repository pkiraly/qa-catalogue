package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#931
 */
public class Tag931 extends DataFieldDefinition {

  private static Tag931 uniqueInstance;

  private Tag931() {
    initialize();
    postCreation();
  }

  public static Tag931 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag931();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "931";
    label = "KOMITEAN MIETINNÖN LUOVUTTAMISPÄIVÄ - VOYAGER-KENTTÄ";
    mqTag = "KomiteanmietinnönLuovuttamispäivä";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#931";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Komiteanmietinnön luovuttamispäivä", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
