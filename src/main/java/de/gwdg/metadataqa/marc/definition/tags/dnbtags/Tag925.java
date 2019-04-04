package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;

/**
 * Weitere DNB-Codierungen
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag925 extends DataFieldDefinition {

  private static Tag925 uniqueInstance;

  private Tag925() {
    initialize();
    postCreation();
  }

  public static Tag925 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag925();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "925";
    label = "Weitere DNB-Codierungen";
    mqTag = "WeitereDNBCodierungen";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator("First") // TODO: ask what it is, it is not set in the document
      .setCodes(
        "p", "Published on demand (bei Netzpublikationen",
        "r", "Reihenzugehörigkeit",
        "s", "Zugehörigkeit zu einer Sammlung"
      )
      .setMqTag("first");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Code je nach Indikatoren", "R"
    );

    getSubfield("a").setCodes(
      "pd", "Books on demand",
      "ra", "Reihe A",
      "rb", "Reihe B",
      "rc", "Reihe C",
      "rh", "Reihe H",
      "ro", "Reihe O",
      "rm", "Reihe M",
      "rt", "Reihe T",
      "rg", "Fremdsprachige Germanica (1992 - 2003)",
      "ru", "Übersetzung deutschsprachiger Werke (1992 - 2003)",
      "ep", "E-Paper"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
