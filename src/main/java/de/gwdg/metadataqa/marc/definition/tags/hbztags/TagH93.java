package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Lizenzinformationen - LOCAL 093 (H93) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 * https://www.alma-dach.org/alma-marc/holdings/093/093.html
 */
public class TagH93 extends DataFieldDefinition {

  private static TagH93 uniqueInstance;

  private TagH93() {
    initialize();
    postCreation();
  }

  public static TagH93 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH93();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H93";
    label = "LOCAL 093 (H93)";
    mqTag = "LOCAL093";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();


    setSubfieldsWithCardinality(
      "a", "Herkunft, Erfassungsrichtung,Lizenzsatz", "NR",
      "b", "Angaben zu den Zugriffsrechten", "NR",
      "c", "Angaben zur Zahl der parallelen Zugriffe", "NR",
      "d", "Kommentar zu den Zugriffsrechten", "NR",
      "e", "Angaben zum Dokumenttyp", "NR",
      "f", "Angaben zum Lizenztyp", "NR",
      "g", "Angaben zum Preistyp", "NR",
      "h", "Kommentar zu Dokument-, Lizenz- und Preistyp", "NR",
      "8", "ALMA MMS ID linking HOL to HXX elements","R"
    );
  }
}
