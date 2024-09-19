package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Weitere internationale Standardnummer(n) für fortlaufende Sammelwerke (ISSN)
 */
public class Tag029 extends DataFieldDefinition {

  private static Tag029 uniqueInstance;

  private Tag029() {
    initialize();
    postCreation();
  }

  public static Tag029 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag029();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "029";
    label = "Weitere internationale Standardnummer(n) für fortlaufende Sammelwerke (ISSN)";
    mqTag = "WeitereinternationaleStandardnummernfürfortlaufendeSammelwerkeISSN";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446";

    ind1 = new Indicator("nicht definiert")
    .setCodes(  " ", "ISSN formal nicht geprüft",
      "1", "ISSN formal richtig",
      "2", "ISSN formal falsch"
    )
    .setMqTag("NichtDefiniert");
    
    ind2 = new Indicator("nicht definiert")
    .setCodes(  "a", "Autorisierte ISSN",
      "b", "ISSN der Ausgabe auf Datenträger",
      "c", "ISSN der Internetausgabe",
      "d", "ISSN der Druckausgabe"      
    )
    .setMqTag("NichtDefiniert");

    setSubfieldsWithCardinality(
      "a", "Autorisierte ISSN", "NR",
      "b", "ISSN der Ausgabe auf Datenträger", "NR",
      "c", "ISSN der Internetausgabe", "NR",
      "d", "ISSN der Druckausgabe", "NR"
    );


  }
}
