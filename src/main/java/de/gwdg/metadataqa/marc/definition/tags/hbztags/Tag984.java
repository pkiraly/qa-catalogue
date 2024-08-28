package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Provenienzen: Mit dem Exemplar in Beziehung stehende Person/Körperschaft,  Sammlungen
 */
public class Tag984 extends DataFieldDefinition {

  private static Tag984 uniqueInstance;

  private Tag984() {
    initialize();
    postCreation();
  }

  public static Tag984 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag984();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "984";
    label = "Provenienzen: Mit dem Exemplar in Beziehung stehende Person/Körperschaft,  Sammlungen";
    mqTag = "provenienzenBezugPerson";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator("Typ")
    .setCodes(  
      "1", "Person",
      "2", "Körperschaft",
      "3", "Sammlungen"
    )
    .setMqTag("typ");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Bevorzugter Name", "R",
      "b", "Untergeordnete Körperschaft", "R",
      "d", "Datumsangaben in Verbindung mit einem Namen", "R",
      "e", "Fehlende Beziehungskennzeichnungen", "R",
      "g", "Zusatz", "R",
      "0", "GND-Nummer", "R",
      "4", "Beziehungskennzeichen für Akteure, die mit einem Exemplar in Verbindung stehen", "R",
      "8", "Feldverknüpfung und Reihenfolge", "NR"
    );


  }
}
