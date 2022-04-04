package de.gwdg.metadataqa.marc.definition.tags.b3kattags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * DDC (Dewey Decimal Classification) analytisch
 * https://www.bib-bvb.de/web/b3kat/open-data
 */
public class Tag942 extends DataFieldDefinition {

  private static Tag942 uniqueInstance;

  private Tag942() {
    initialize();
    postCreation();
  }

  public static Tag942 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag942();
    return uniqueInstance;
  }

  private void initialize() {

    tag = "942";
    label = "DDC (Dewey Decimal Classification) analytisch";
    mqTag = "DDCAnalytisch";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://www.bib-bvb.de/web/b3kat/open-data";
    // setCompilanceLevels("O");

    ind1 = new Indicator("")
      .setCodes("1", "");

    ind2 = new Indicator("")
      .setCodes("1", "");

    setSubfieldsWithCardinality(
      "c", "Grundnotation (Sachaspekt)", "NR",
      "f", "Notation aus Hilfstafel 1 (Zeitaspekt)", "NR",
      "g", "Notation aus Hilfstafel 2 (Geographischer Aspekt)", "NR",
      "e", "Angabe der zugrunde liegenden DDC-Ausgabe", "NR"
    );

    getSubfield("c").setMqTag("Grundnotation");
    getSubfield("f").setMqTag("NotationAusHilfstafel1Zeitaspekt");
    getSubfield("g").setMqTag("NotationAusHilfstafel2GeographischerAspekt");
    getSubfield("e").setMqTag("AngabeDerZugrundeLiegendenDDCAusgabe");
  }
}
