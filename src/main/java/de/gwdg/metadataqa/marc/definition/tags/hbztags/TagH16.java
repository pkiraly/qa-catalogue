package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	National Bibliographic Agency Control Number (H16) from ALMA Publishing Holdings information (Hxx)
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on https://www.loc.gov/marc/holdings/hd016.html
 */
public class TagH16 extends DataFieldDefinition {

  private static TagH16 uniqueInstance;

  private TagH16() {
    initialize();
    postCreation();
  }

  public static TagH16 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagH16();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "H16";
    label = "National Bibliographic Agency Control Number (H16) - Marc 016 024";
    mqTag = "H16NationalBibliographicAgencyControlNumber";
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
