package de.gwdg.metadataqa.marc.utils.pica;

public class FieldPath {
  String field;
  String subfield;

  public FieldPath(String field, String subfield) {
    this.field = field;
    this.subfield = subfield;
  }

  public String getField() {
    return field;
  }

  public String getSubfield() {
    return subfield;
  }
}
