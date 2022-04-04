package de.gwdg.metadataqa.marc.utils.pica;

public class OccurrenceRange {
  private int unitLength = 0;
  private int start;
  private int end;
  private final String format;

  public OccurrenceRange(int unitLength, int start, int end) {
    this.unitLength = unitLength;
    this.start = start;
    this.end = end;
    this.format = "%0" + unitLength + "d";
  }

  public int getUnitLength() {
    return unitLength;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  @Override
  public String toString() {
    return String.format(format + "-" + format, start, end);
  }

  public boolean validate(String occurrence) {
    if (occurrence.length() != unitLength)
      return false;
    try {
      int number = Integer.parseInt(occurrence);
      return (start <= number && number <= end);
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
