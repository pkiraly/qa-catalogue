package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 * Lizenzinformationen
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686
 */
public class Tag093 extends DataFieldDefinition {

  private static Tag093 uniqueInstance;

  private Tag093() {
    initialize();
    postCreation();
  }

  public static Tag093 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag093();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "093";
    label = "Lizenzinformationen";
    mqTag = "Lizenzinformationen";
    cardinality = Cardinality.Nonrepeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=698777686";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Herkunft, Erfassungsrichtung", "NR",
      "b", "Angaben zu den Zugriffsrechten", "NR",
      "c", "Angaben zur Zahl der parallelen Zugriffe Freitext","NR",
      "d", "Kommentar zu den Zugriffsrechten Freitext","NR",
      "e", "Angaben zum Dokumenttyp","NR",      
      "f", "Angaben zum Lizenztyp","NR",  
      "g", "Angaben zum Preistyp","NR",
      "h", "Kommentar zu Dokument-, Lizenz- und Preistyp Freitext","NR"
    );
  }
}
