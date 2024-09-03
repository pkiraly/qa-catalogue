package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Alte Drucke/Provenienz LOCAL 992 (HAD) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 - https://www.alma-dach.org/alma-marc/holdings/992/992.html
 */
public class TagHAD extends DataFieldDefinition {

  private static TagHAD uniqueInstance;

  private TagHAD() {
    initialize();
    postCreation();
  }

  public static TagHAD getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagHAD();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "HAD";
    label = "LOCAL 992 (HAD)";
    mqTag = "LOCAL992";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();


    setSubfieldsWithCardinality(
      "a", "Zweigstelle", "NR",
      "b", "Exemplarspezifische bibliographische Angaben", "NR",
      "c", "Illustration(en) /Buchmalerei Schreibstoff / Beschreibstoff Wasserzeichen", "NR",
      "d", "Einband", "NR",
      "e", "Exemplarhinweise", "NR",
      "f", "Signaturvermerk", "NR",
      "g", "Standortvermerk (Standort, Collection)", "NR",
      "h", "Aufstellungssystematikvermerk", "NR",
      "k", "Historische Kaufvermerke", "NR",
      "l", "Literaturhinweise", "NR",
      "m", "Marginalien", "NR",
      "p", "Provenienz", "R",
      "q", "Raubgutkennung (CV-Liste verfügbar)", "NR",
      "r", "Restitutionsstatus (CV-Liste verfügbar)", "NR",
      "s", "Alte Signatur", "R",
      "8", "ALMA MMS ID linking HOL to HXX elements","R"
    );
  }
} 