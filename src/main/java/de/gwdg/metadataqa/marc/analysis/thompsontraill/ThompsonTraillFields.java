package de.gwdg.metadataqa.marc.analysis.thompsontraill;

public enum ThompsonTraillFields {
  ID("id", "id"),
  ISBN("ISBN", "isbn"),
  AUTHORS("Authors", "authors"),
  ALTERNATIVE_TITLES("Alternative Titles", "alternative-titles"),
  EDITION("Edition", "edition"),
  CONTRIBUTORS("Contributors", "contributors"),
  SERIES("Series", "series"),
  TOC("Table of Contents and Abstract", "toc-and-abstract"),
  DATE_008("Date 008", "date-008"),
  DATE_26X("Date 26X", "date-26x"),
  LC_NLM("LC/NLM Classification", "classification-lc-nlm", true),
  LOC("Subject Headings: Library of Congress", "classification-loc", true),
  MESH("Subject Headings: Mesh", "classification-mesh", true),
  FAST("Subject Headings: Fast", "classification-fast", true),
  GND("Subject Headings: GND", "classification-gnd", true),
  OTHER("Subject Headings: Other", "classification-other", true),
  ONLINE("Online", "online"),
  LANGUAGE_OF_RESOURCE("Language of Resource", "language-of-resource"),
  COUNTRY_OF_PUBLICATION("Country of Publication", "country-of-publication"),
  LANGUAGE_OF_CATALOGING("Language of Cataloging", "no-language-or-english"),
  RDA("Descriptive cataloging standard is RDA", "rda"),
  TOTAL("total", "total")
  ;

  private String label;
  private String machine;
  private boolean isClassification = false;

  ThompsonTraillFields(String label, String machine) {
    this.label = label;
    this.machine = machine;
  }

  ThompsonTraillFields(String label, String machine, boolean isClassification) {
    this(label, machine);
    this.isClassification = isClassification;
  }

  public String getLabel() {
    return label;
  }

  public String getMachine() {
    return machine;
  }

  public boolean isClassification() {
    return isClassification;
  }

  @Override
  public String toString() {
    return "ThompsonTrailFields{" +
      "label='" + label + '\'' +
      ", machine='" + machine + '\'' +
      ", isClassification=" + isClassification +
      '}';
  }
}
