package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Ausgabebezeichnung in normierter Form
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag259 extends DataFieldDefinition {

  private static Tag259 uniqueInstance;

  private Tag259() {
    initialize();
    postCreation();
  }

  public static Tag259 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag259();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "259";
    label = "Ausgabebezeichnung in normierter Form";
    mqTag = "WeitereTitel";
    cardinality = Cardinality.Nonrepeatable;
    // descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Ausgabebezeichnung in normierter Form", "R"
    );

    getSubfield("a")
      .setMqTag("rdf:value");
  }
}
