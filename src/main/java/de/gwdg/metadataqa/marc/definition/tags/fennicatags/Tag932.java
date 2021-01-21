package de.gwdg.metadataqa.marc.definition.tags.fennicatags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#932
 */
public class Tag932 extends DataFieldDefinition {

  private static Tag932 uniqueInstance;

  private Tag932() {
    initialize();
    postCreation();
  }

  public static Tag932 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag932();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "932";
    label = "KOMITEAN PUHEENJOHTAJA - VOYAGER-KENTTÄ";
    mqTag = "KomiteanPuheenjohtaja";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://www.kansalliskirjasto.fi/extra/marc21/bib/omat.htm#932";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Komitean puheenjohtaja: sukunimi, etunimi", "NR",
      "c", "Muut lisäykset", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
