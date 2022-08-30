package de.gwdg.metadataqa.marc.utils.pica;

public class PicaRange {
  private String raw;
  private String start;
  private String end;
  private Integer unitLength = null;
  private boolean hasRange;

  public PicaRange(String raw) {
    this.raw = raw;
    if (raw != null)
      parse();
  }

  private void parse() {
    String[] parts = raw.split("-");
    hasRange = (parts.length == 2);
    start = parts[0];
    if (hasRange)
      end = parts[1];
    unitLength = start.length();
  }

  public String getStart() {
    return start;
  }

  public String getEnd() {
    return end;
  }

  public int getUnitLength() {
    return unitLength == null ? 0 : unitLength;
  }

  public boolean isHasRange() {
    return hasRange;
  }

  public boolean isNull() {
    return raw == null;
  }

  @Override
  public String toString() {
    return "PicaRange{" +
      "raw='" + raw + '\'' +
      ", start='" + start + '\'' +
      ", end='" + end + '\'' +
      ", unitLength=" + unitLength +
      ", hasRange=" + hasRange +
      '}';
  }
}
