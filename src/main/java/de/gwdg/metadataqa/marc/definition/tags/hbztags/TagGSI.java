package de.gwdg.metadataqa.marc.definition.tags.hbztags;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;

/**
 *   	Identifier/System Control Number (GSI) from ALMA Publishing GND Authority Enrichment
 * https://service-wiki.hbz-nrw.de/pages/viewpage.action?pageId=949911658 based on MARC Version of https://wiki.dnb.de/pages/viewpage.action?pageId=50759357&preview=/50759357/100664257/024.pdfhttps://www.alma-dach.org/alma-marc/authority/024/024.html
 * https://www.alma-dach.org/alma-marc/authority/024/024.html
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

    ind1 = new Indicator("Art der Standard-Nummer oder des Codes")
    .setCodes(
      "7", "Quelle näher beschrieben im Unterfeld $2",
      "8", "Unspezifizierte Art der Standardnummer oder des Codes"
    )
    .setMqTag("artDerStandard-NummerOderDesCodes");
    ind2 = new Indicator();

    setSubfieldsWithCardinality(
      "a", "Standardnummer oder Code", "NR",
      "0", "Standardnummer oder Code", "NR",
      "2", "Quelle der Nummer oder des Codes", "NR",
      "9", "5: - Zuständige Institution (NW), C: - Anwendungskontext (W), v: - Bemerkungen (NW)", "R",   
      "A", "Vocabulary subfield", "R",
      "B", "Authority ID subfield", "R",
      "C", "Authority tag subfield","R"
    );
  }
}
