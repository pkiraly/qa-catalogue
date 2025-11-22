package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Thema-Klassifikation
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&amp;val=4010
 */
public class Tag926 extends DataFieldDefinition {

  private static Tag926 uniqueInstance;

  private Tag926() {
    initialize();
    postCreation();
  }

  public static Tag926 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag926();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "926";
    label = "Thema-Klassifikation";
    mqTag = "ThemaKlassifikation";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator("Level der Thema-Klassifikation")
      .setCodes(
        "1", "Primär (aus ONIX mainsubject)",
        "2", "Sekundär (aus ONIX subject)"
      )
      .setMqTag("level");

    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Thema-Code (Subjects + Qualifier)", "NR",
      "o", "ONIX-Code für Thema-Klasse", "NR",
      "q", "Thema-Quelle (Sourcename)", "NR",
      "v", "Thema-Version", "NR",
      "x", "Thema-Text", "NR"
    );

    getSubfield("o").setCodes(
      "93", "Thema subject category",
      "94", "Thema geographical qualifier",
      "95", "Thema language qualifier",
      "96", "Thema time period qualifier",
      "97", "Thema educational purpose qualifier",
      "98", "Thema interest age / special interest qualifier",
      "99", "Thema style qualifier"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
