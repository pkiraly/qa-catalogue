package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Textual Physical Form Designator (H42) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd842.html
 */
public class TagH42 extends DataFieldDefinition {

  private static TagH42 uniqueInstance;

  private TagH42() {
    initialize();
    postCreation();
  }

  public static TagH42 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH42();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H42";
    label = "Textual Physical Form Designator (H42) - Marc 842";
    mqTag = "H42TextualPhysicalFormDesignator";
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
