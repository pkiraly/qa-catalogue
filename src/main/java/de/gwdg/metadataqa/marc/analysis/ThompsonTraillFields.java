package de.gwdg.metadataqa.marc.analysis;

public enum ThompsonTraillFields {
  ID("id", "id"),
  ISBN("ISBN", "isbn"),
  Authors("Authors", "authors"),
  AlternativeTitles("Alternative Titles", "alternative-titles"),
  Edition("Edition", "edition"),
  Contributors("Contributors", "contributors"),
  Series("Series", "series"),
  TOC("Table of Contents and Abstract", "toc-and-abstract"),
  Date008("Date 008", "date-008"),
  Date26X("Date 26X", "date-26x"),
  LcNlm("LC/NLM Classification", "classification-lc-nlm", true),
  Loc("Subject Headings: Library of Congress", "classification-loc", true),
  Mesh("Subject Headings: Mesh", "classification-mesh", true),
  Fast("Subject Headings: Fast", "classification-fast", true),
  GND("Subject Headings: GND", "classification-gnd", true),
  Other("Subject Headings: Other", "classification-other", true),
  Online("Online", "online"),
  LanguageOfResource("Language of Resource", "language-of-resource"),
  CountryOfPublication("Country of Publication", "country-of-publication"),
  LanguageOfCataloging("Language of Cataloging", "no-language-or-english"),
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
