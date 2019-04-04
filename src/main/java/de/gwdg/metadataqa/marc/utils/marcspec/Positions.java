package de.gwdg.metadataqa.marc.utils.marcspec;

public class Positions {

  private Position start;
  private Position end;
  private Integer length;
  private boolean isRange;

  public Positions() {}

  public Positions(Position start, Position end, Integer length) {
    this.start = start;
    this.end = end;
    this.length = length;
  }

  public Position getStart() {
    return start;
  }

  public void setStart(Position start) {
    this.start = start;
  }

  public Position getEnd() {
    return end;
  }

  public void setEnd(Position end) {
    this.end = end;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public void setRange(boolean range) {
    isRange = range;
  }

  public boolean isRange() {
    return isRange;
  }

  public String extract(String input) {
    int first = start.isNumeric() ? start.value() : 0;
    if (!isRange) {
      if (start.isWildcard())
        return input.substring(input.length()-1);

      return input.substring(first, first + 1);
    } else {

      if (start.isWildcard()
        && end.isNumeric()
        &&  end.value() + 1 < input.length() - 1)
        return input.substring(input.length() - end.value());

      if (end.isNumeric() && end.value() + 1 < input.length() - 1)
        return input.substring(first, end.value() + 1);
      else
        return input.substring(first);
    }
  }

  @Override
  public String toString() {
    return "Positions{" +
      "start=" + start +
      ", end=" + end +
      ", length=" + length +
      '}';
  }

}
