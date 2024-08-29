package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Leihverkehrsangaben - LOCAL 092 (H92) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 * https://www.alma-dach.org/alma-marc/holdings/092/092.html
 */
public class TagH92 extends DataFieldDefinition {

  private static TagH92 uniqueInstance;

  private TagH92() {
    initialize();
    postCreation();
  }

  public static TagH92 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH92();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H92";
    label = "LOCAL 092 (H92)";
    mqTag = "LOCAL092";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();


    setSubfieldsWithCardinality(
        "a", "Bibliothekskennung (BIK)", "NR",
        "d", "ZDB-Paket-Sigel", "R",
        "k", "Leihverkehrs-Region", "NR",
        "l", "Leihverkehrsrelevanz", "NR",
        "o", "Fernleihindikator", "NR",
        "p", "Vertragsrechtl. Einschränkungen bei der Fernleihe", "R",
        "q", "Kommentar zum Fernleihindikator", "NR",
        "u", "SSG-Nummer und/oder Angaben zur DFG-Förderung", "NR",
        "w", "SSG-Notation", "R",
        "y", "ZDB-Prioritätszahl", "R",
        "z", "Finanzierungsart", "NR",
        "8", "ALMA MMS ID linking HOL to HXX elements","R"
    );
  }
}
