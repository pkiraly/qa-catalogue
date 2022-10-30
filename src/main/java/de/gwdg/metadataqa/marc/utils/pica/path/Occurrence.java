package de.gwdg.metadataqa.marc.utils.pica.path;

public class Occurrence {
  public enum Type {
    SINGLE,
    RANGE,
    ALL
  }

  private Type type;
  private Integer start;
  private Integer end;

  public Occurrence(Type type, Integer start, Integer end) {
    this.type = type;
    this.start = start;
    this.end = end;
  }

  public Type getType() {
    return type;
  }

  public Integer getStart() {
    return start;
  }

  public Integer getEnd() {
    return end;
  }
}
