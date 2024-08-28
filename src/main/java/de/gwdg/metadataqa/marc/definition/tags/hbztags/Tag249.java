package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Weitere Titel etc. bei Zusammenstellungen (Abweichend zur DNB)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446
 *  DNB: http://swbtools.bsz-bw.de/cgi-bin/help.pl?cmd=kat&val=4010
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
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Weiterer Titel bei Zusammenstellungen", "R",
      "b", "Titelzusätze zur gesamten Zusammenstellung", "NR",
      "c", "Verantwortlichkeitsangabe zur gesamten Zusammenstellung", "NR",
      "v", "Verantwortlichkeitsangabe zum weiteren Titel", "R"
    );

    getSubfield("a")
      .setMqTag("rdf:value");
  }
}
