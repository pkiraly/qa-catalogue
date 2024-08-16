package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	LOCAL 695 (H95) from ALMA Publishing  Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658
 */
public class TagH95 extends DataFieldDefinition {

  private static TagH95 uniqueInstance;

  private TagH95() {
    initialize();
    postCreation();
  }

  public static TagH95 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH95();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H95";
    label = "LOCAL 695 (H95)";
    mqTag = "LOCAL695";
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
