package de.gwdg.metadataqa.marc.definition.tags.zbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

public class Tag520 extends DataFieldDefinition {

  private static Tag520 uniqueInstance;

  private Tag520() {
    initialize();
    postCreation();
  }

  public static Tag520 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag520();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "520";
    label = "Zusammenfassung des Inhalts, Abstract usw.";
    mqTag = "ZusammenfassungInhaltAbstract";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "5", "Datum und Kürzel des Bearbeiters/der Bearbeiterin", "NR"
    );

    getSubfield("5").setMqTag("rdf:value");
  }
}
