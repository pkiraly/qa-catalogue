package de.gwdg.metadataqa.marc.utils.marcspec;

public class Range {
  private String start;
  private String end;
  private Boolean range;

  public Range(String start) {
    this.start = start;
  }

  public Range(String start, String end) {
    this.start = start;
    this.end = end;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public boolean isRange() {
    if (range == null) {
      range = (
        start != null && end != null
        // && fieldPositionStart < fieldPositionEnd
      );
    }
    return range;
  }

  @Override
  public String toString() {
    return "Position{" +
      "start='" + start + '\'' +
      ", end='" + end + '\'' +
      ", range=" + range +
      '}';
  }

  public String encode() {
    String encoded = start;
    if (end != null)
      encoded += "-" + end;
    return encoded;
  }
}
