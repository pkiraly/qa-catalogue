package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#906
 */
public class Tag906 extends DataFieldDefinition {

  private static Tag906 uniqueInstance;

  private Tag906() {
    initialize();
    postCreation();
  }

  public static Tag906 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag906();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "906";
    label = "JATKUVAN JULKAISUN ALUEELLINEN JA KIELELLINEN KATTAVUUSKOODI - VOYAGER-KENTTÄ";
    mqTag = "JatkuvanJulkaisunAlueellinenJaKielellinenKattavuuskoodi";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#906";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Koodi", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
