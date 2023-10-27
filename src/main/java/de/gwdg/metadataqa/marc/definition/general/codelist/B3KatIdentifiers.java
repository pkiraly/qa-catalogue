package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.utils.EncodedValueFileReader;

/**
 * Übersicht über die am B3Kat beteiligten Bibliotheken
 * https://www.bib-bvb.de/BibList/b3kat-biblist.html
 */
public class B3KatIdentifiers extends CodeList {

  private void initialize() {
    name = "MARC Organization Codes";
    url = "https://www.loc.gov/marc/organizations/orgshome.html";
    codes = EncodedValueFileReader.fileToCodeList("marc/b3kat.isil.csv");
    indexCodes();
  }

  private static B3KatIdentifiers uniqueInstance;

  private B3KatIdentifiers() {
    initialize();
  }

  public static B3KatIdentifiers getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new B3KatIdentifiers();
    return uniqueInstance;
  }
}
