package de.gwdg.metadataqa.marc.definition.tags.dnbtags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Angaben zum umgelenkten Datensatz
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&amp;val=4010
 */
public class Tag889 extends DataFieldDefinition {

  private static Tag889 uniqueInstance;

  private Tag889() {
    initialize();
    postCreation();
  }

  public static Tag889 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag889();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "889";
    label = "Angaben zum umgelenkten Datensatz";
    mqTag = "AngabenZumUmgelenktenDatensatz";
    cardinality = Cardinality.Repeatable;
    // descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "w", "Replacement bibliographic record control number (Kontrollnummer des Zielsatzes)", "R"
    );

    getSubfield("w").setMqTag("rdf:value");
  }
}
