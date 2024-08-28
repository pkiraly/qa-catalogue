package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Provenienzen: Exemplarbeschreibung
 */
public class Tag985 extends DataFieldDefinition {

  private static Tag985 uniqueInstance;

  private Tag985() {
    initialize();
    postCreation();
  }

  public static Tag985 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag985();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "985";
    label = "Provenienzen: Exemplarbeschreibung";
    mqTag = "provenienzenExemplarbeschreibung";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=518750316";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Exemplarspezifische bibliographische Angaben", "R",
      "b", "Exemplarhinweise	", "R",
      "c", "Erhaltungszustand	", "R",
      "d", "Bestandserhaltungsmaßnahmen	", "R",
      "e", "Bemerkungen Erhaltungszustand	", "R",
      "f", "Signaturvermerk	", "R",
      "g", "Standortvermerk (Standort, Collection)	", "R",
      "h", "Aufstellungssystematik	", "R",
      "i", "Historische Kaufvermerke	", "R",
      "j", "Alte Signatur	", "R",
      "k", "Akzessionsnummer	", "R",
      "l", "Literaturhinweise	", "R",
      "m", "Marginalien	", "R",
      "n", "Illustrationen/Buchmalerei Schreibstoff/Beschreibstoff, Wasserzeichen	", "R",
      "o", "Provenienz Beschreibung	", "R",
      "p", "Provenienz unvollständig	", "R",
      "q", "Provenienz Referenz	", "R",
      "r", "Provenienz Bemerkung	", "R",
      "s", "Raubgutkennung	SISIS 1311 (ULB Münster)", "R",
      "t", "Restitutionsstatus	", "R",
      "u", "Scan", "R",
      "8", "Feldverknüpfung und Reihenfolge", "NR"
    );

  }
}
