package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	LOCAL 859 (H59) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagH59 extends DataFieldDefinition {

  private static TagH59 uniqueInstance;

  private TagH59() {
    initialize();
    postCreation();
  }

  public static TagH59 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH59();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H59";
    label = "LOCAL 859 (H59)";
    mqTag = "LOCAL859";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    // TODO: Needs to be adjusted:
    // setSubfieldsWithCardinality(
    //   "0", "Nummer/Code", "R",
    //     "S", "Quelle/Code der Standardnummer", "NR",
    //     "v", "Bemerkung", "NR",        
    //     "A", "Vocabulary subfield", "R",
    //     "B", "Authority ID subfield", "R",
    //     "C", "Authority tag subfield","R"
    // );
  }
}
