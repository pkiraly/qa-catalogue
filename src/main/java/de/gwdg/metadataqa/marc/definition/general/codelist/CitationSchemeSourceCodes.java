package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Citation Scheme Source Codes
 * https://www.loc.gov/standards/sourcelist/citation.html
 * used in Bibliographic records 524 $2 (Preferred Citation of Described Materials Note / Source of schema used)
 */
public class CitationSchemeSourceCodes extends CodeList {

  private void initialize() {
    name = "Citation Scheme Source Codes";
    url = "https://www.loc.gov/standards/sourcelist/citation.html";
    codes = Utils.generateCodes(
      "bdlc", "Bieber's dictionary of legal citation (Buffalo, NY: W.S.Hein)",
      "flr", "Fundamentals of legal research (Westbury, NY: Foundation Press)",
      "glc", "Guide to legal citation and sources of citation aid (Don Mills, ON: De Boo)",
      "nlmrfbc", "National Library of Medicine recommended formats for bibliographic citation (Bethesda, MD: U.S. Dept. of Health and Human Services, Public Health Service, National Institutes of Health, National Library of Medicine)",
      "publshr", "Publisher [Used when a citation is suggested by the publisher of the item]",
      "ssrq", "Swiss law sources (Sammlung Schweizerischer Rechtsquellen)",
      "usc", "A Uniform system of citation (Cambridge, MA: Harvard Law Review Association)"
    );
    indexCodes();
  }

  private static CitationSchemeSourceCodes uniqueInstance;

  private CitationSchemeSourceCodes() {
    initialize();
  }

  public static CitationSchemeSourceCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new CitationSchemeSourceCodes();
    return uniqueInstance;
  }
}