package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in Gent
 */
public class Tag503 extends DataFieldDefinition {

  private static Tag503 uniqueInstance;

  private Tag503() {
    initialize();
    postCreation();
  }

  public static Tag503 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag503();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "503";
    label = "Kötetadatok";
    mqTag = "Kotetadatok";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/503.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "b", "Kiadás", "NR",
      "c", "Megjelenési adatok", "NR",
      "e", "Fizikai leírás", "NR",
      "f", "Sorozati adatok", "NR",
      "g", "Kötetszám", "NR",
      "m", "Anyagspecifikus adatok", "NR",
      "n", "Megjegyzés", "NR",
      "q", "zenei ETO (FSZEK)", "NR",
      "r", "Szerzõségi közlés", "NR",
      "t", "Cím", "NR",
      "v", "kötet TO (FSZEK)", "NR",
      "w", "kötet ETO", "NR",
      "x", "ISSN", "NR",
      "y", "ISBN", "NR",
      "z", "szakjelzet + Cutter", "NR"
    );
  }
}
