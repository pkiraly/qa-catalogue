package de.gwdg.metadataqa.marc.definition.tags.sztetags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Locally defined field in SZTE
 */
public class Tag695 extends DataFieldDefinition {

  private static Tag695 uniqueInstance;

  private Tag695() {
    initialize();
    postCreation();
  }

  public static Tag695 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag695();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "695";
    label = "Local subject term";
    mqTag = "LocalSubjectTerm";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://vocal.lib.klte.hu/marc/bib/695.html";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Main subject term", "NR",
      "b", "Geographic subterm", "NR",
      "c", "Place of the event", "NR",
      "d", "Active dates", "NR",
      "e", "Relation term", "NR",
      "x", "General subdivision", "R",
      "y", "Chronological subdivision", "R",
      "z", "Geographical subdivision", "R"
    );
  }
}
