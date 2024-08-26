package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Online-Contents-Ausschnittskennung - ZDB
 * http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
 */
public class Tag932 extends DataFieldDefinition {

  private static Tag932 uniqueInstance;

  private Tag932() {
    initialize();
    postCreation();
  }

  public static Tag932 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag932();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "932";
    label = "Online-Contents-Ausschnittskennung - ZDB";
    mqTag = "OnlineContentsAusschnittskennungZDB";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Online-Contents-Ausschnittskennung", "R"
    );

    getSubfield("a").setMqTag("rdf:value");
  }
}
