package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Identifier/System Control Number (GSI) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on PICA+ Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664257/024.pdf
 */
public class TagGSI extends DataFieldDefinition {

  private static TagGSI uniqueInstance;

  private TagGSI() {
    initialize();
    postCreation();
  }

  public static TagGSI getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new TagGSI();
    return uniqueInstance;
  }

  private void initialize() {
    tag = "GSI";
    label = "Identifier/System Control Number (GSI) - GND 024";
    mqTag = "GNDIdentifierSystemControlNumber";
    cardinality = Cardinality.Repeatable;
    descriptionUrl = "https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658";

    ind1 = new Indicator();
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "0", "Nummer/Code", "R",
        "S", "Quelle/Code der Standardnummer", "NR",
        "v", "Bemerkung", "NR",        
        "A", "Vocabulary subfield", "R",
        "B", "Authority ID subfield", "R",
        "C", "Authority tag subfield","R"
    );
  }
}
