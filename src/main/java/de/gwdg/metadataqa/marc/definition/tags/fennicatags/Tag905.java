package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#905
 */
public class Tag905 extends DataFieldDefinition {

  private static Tag905 uniqueInstance;

  private Tag905() {
    initialize();
    postCreation();
  }

  public static Tag905 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag905();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "905";
    label = "JATKUVAN JULKAISUN TYYPPIKOODI - VOYAGER-KENTTÄ";
    mqTag = "JatkuvanJulkaisunTyyppikoodi";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#905";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Koodi", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
