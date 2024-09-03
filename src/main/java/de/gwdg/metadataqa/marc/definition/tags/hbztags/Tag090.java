package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Weitere Codierungen
 */
public class Tag090 extends DataFieldDefinition {

  private static Tag090 uniqueInstance;

  private Tag090() {
    initialize();
    postCreation();
  }

  public static Tag090 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag090();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "090";
    label = "Weitere Codierungen";
    mqTag = "WeitereCodierungen";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=525369446";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Papierzustand", "R",
      "b", "Audiovisuelles Medium / Bildliche Darstellung", "R",
      "f", "Erscheinungsform", "R",
      "g", "Veröffentlichungsart und Inhalt Mono", "R",
      "h", "Literaturtyp", "R",
      "i", "Angaben zur Freiwilligen Selbstkontrolle der Filmwirtschaft (FSK)", "R",
      "n", "Veröffentlichungsart und Inhalt (ZDB)", "R",
      "o", "Frühere Erscheinungsform", "R",
      "v", "Nachlassmaterialien", "R",      
      "w", "Authentizitätsgrad", "R"          
    );


  }
}
