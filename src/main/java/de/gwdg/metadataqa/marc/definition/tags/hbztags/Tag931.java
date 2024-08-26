package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Gravierende Korrektur - ZDB
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag931 extends DataFieldDefinition {

  private static Tag931 uniqueInstance;

  private Tag931() {
    initialize();
    postCreation();
  }

  public static Tag931 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag931();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "931";
    label = "Gravierende Korrektur - ZDB";
    mqTag = "GravierendeKorrekturZDB";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Datum (JJ-MM-TT)", "NR",
      "b", "Selektionsschlüssel", "NR"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
