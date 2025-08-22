package de.gwdg.metadataqa.marc.utils.marcspec;

public class Subfield {
  private String subfield;
  private Range position;
  private Range index;

  public Subfield() {
  }

  public Subfield(String subfield) {
    this.subfield = subfield;
  }

  public String getSubfield() {
    return subfield;
  }

  public void setSubfield(String subfield) {
    this.subfield = subfield;
  }

  public Range getPosition() {
    return position;
  }

  public boolean hasPosition() {
    return position != null;
  }

  public void setPosition(Range position) {
    this.position = position;
  }

  public Range getIndex() {
    return index;
  }

  public boolean hasIndex() {
    return index != null;
  }

  public void setIndex(Range index) {
    this.index = index;
  }

  @Override
  public String toString() {
    return "Subfield{" +
      "subfield='" + subfield + '\'' +
      ", position=" + position +
      ", index=" + index +
      '}';
  }

  public String encode() {
    String encoded = subfield;
    if (hasIndex())
      encoded += "[" + index.encode() + "]";
    if (hasPosition())
      encoded += "/" + position.encode();
    return encoded;
  }
}
