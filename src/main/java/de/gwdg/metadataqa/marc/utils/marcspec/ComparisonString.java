package de.gwdg.metadataqa.marc.utils.marcspec;

public class ComparisonString {
  private String raw;
  private String comparable;

  public ComparisonString(String raw) {
    this.raw = raw;
  }

  public String getRaw() {
    return raw;
  }

  public void setRaw(String raw) {
    this.raw = raw;
  }

  public String getComparable() {
    return comparable;
  }

  public void setComparable(String comparable) {
    this.comparable = comparable;
  }
}
