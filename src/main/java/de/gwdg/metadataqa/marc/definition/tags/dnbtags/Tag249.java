package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Weitere Titel etc. bei Zusammenstellungen
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag249 extends DataFieldDefinition {

  private static Tag249 uniqueInstance;

  private Tag249() {
    initialize();
    postCreation();
  }

  public static Tag249 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag249();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "249";
    label = "Weitere Titel etc. bei Zusammenstellungen";
    mqTag = "WeitereTitel";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Weiterer Titel bei Zusammenstellungen", "R",
      "b", "Titelzusätze zur gesamten Zusammenstellung", "NR",
      "v", "Verantwortlichkeitsangabe zum weiteren Titel", "R"
    );

    getSubfield("a")
      .setMqTag("rdf:value");
  }
}
