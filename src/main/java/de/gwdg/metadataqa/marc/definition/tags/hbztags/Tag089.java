package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Dewey Decimal Classification Number (analytisch)
 */
public class Tag089 extends DataFieldDefinition {

  private static Tag089 uniqueInstance;

  private Tag089() {
    initialize();
    postCreation();
  }

  public static Tag089 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag089();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "089";
    label = "Dewey Decimal Classification Number (analytisch)";
    mqTag = "DeweyDecimalClassificationNumberAnalytisch";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446";

    ind1 = new Indicator("Ausgabe")
    .setCodes(
      " ", "No information provided",      
      "0", "Vollversion",
      "1", "Kurzausgabe"
    )
    .setMqTag("ausgabe");

    ind2 = new Indicator("Nicht erstellt von LC")
    .setCodes(
      " ", "No information provided",      
      "4", "Erstellt nicht von LC"
    )
    .setMqTag("nichtVonLCERstellt");

    setSubfieldsWithCardinality(
      "a", "Vollständige Notation", "NR",
      "b", "Exemplarnummer (Item number)", "NR",
      "c", "Grundnotation", "NR",
      "d", "Notationen anderer Haupttafeln", "R",
      "e", "Angabe der zugrunde liegenden DDC-Ausgabe", "NR",
      "f", "Notation aus Hilfstafel 1", "R",
      "g", "Notation aus Hilfstafel 2", "R",
      "h", "Notation aus Hilfstafel 3A", "R",
      "i", "Notation aus Hilfstafel 3B", "R",
      "j", "Notation aus Hilfstafel 3C", "R",
      "k", "Notation aus Hilfstafel 4", "R",
      "l", "Notation aus Hilfstafel 5", "R",
      "m", "Notation aus Hilfstafel 6", "R",
      "t", "Notation aus einer Anhaengetafel", "R",
      "A","Quelle der vergebenen Notation","R"
    );


  }
}
