package de.gwdg.metadataqa.marc.utils.marcspec;

public class SubTerm {
  private MARCspec marcSpec;
  private ComparisonString comparisonString;

  public SubTerm() {

  }

  public SubTerm(MARCspec marcSpec, ComparisonString comparisonString) {
    this.marcSpec = marcSpec;
    this.comparisonString = comparisonString;
  }

  public MARCspec getMarcSpec() {
    return marcSpec;
  }

  public void setMarcSpec(MARCspec marcSpec) {
    this.marcSpec = marcSpec;
  }

  public ComparisonString getComparisonString() {
    return comparisonString;
  }

  public void setComparisonString(ComparisonString comparisonString) {
    this.comparisonString = comparisonString;
  }

  @Override
  public String toString() {
    return "SubTerm{" +
      "marcSpec=" + marcSpec +
      ", comparisonString=" + comparisonString +
      '}';
  }
}
