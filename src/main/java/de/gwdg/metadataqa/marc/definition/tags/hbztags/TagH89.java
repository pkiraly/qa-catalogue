package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	LOCAL 689 (H89) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagH89 extends DataFieldDefinition {

  private static TagH89 uniqueInstance;

  private TagH89() {
    initialize();
    postCreation();
  }

  public static TagH89 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH89();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H89";
    label = "LOCAL 689 (H89)";
    mqTag = "LOCAL689";
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
