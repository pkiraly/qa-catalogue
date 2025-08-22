package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.utils.SchemaSpec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MarcSpec implements SchemaSpec, Serializable {
  private String tag;
  private String indicator;
  private Range position;
  private Range index;
  private List<Subfield> subfields = new ArrayList<>();

  public MarcSpec() {
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public boolean isMasked() {
    return tag.contains(".");
  }

  public String getIndicator() {
    return indicator;
  }

  public boolean hasIndicator() {
    return indicator != null;
  }

  public void setIndicator(String indicator) {
    this.indicator = indicator;
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

  public List<Subfield> getSubfields() {
    return subfields;
  }

  public boolean hasSubfields() {
    return subfields != null && !subfields.isEmpty();
  }

  public void setSubfields(List<Subfield> subfields) {
    this.subfields = subfields;
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
    return "MarcSpec2{" +
      "tag='" + tag + '\'' +
      ", indicator='" + indicator + '\'' +
      ", position=" + position +
      ", index=" + index +
      ", subfields=" + subfields +
      '}';
  }

  @Override
  public String encode() {
    String encoded = tag;
    if (hasIndex())
      encoded += "[" + index.encode() + "]";
    if (hasPosition())
      encoded += "/" + position.encode();
    if (hasIndicator())
      encoded += "^" + indicator;
    if (hasSubfields()) {
      for (Subfield subfield : subfields)
        encoded += "$" + subfield.encode();
    }
    return encoded;
  }
}
