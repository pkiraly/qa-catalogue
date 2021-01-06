package de.gwdg.metadataqa.marc.model.validation;

public enum ValidationErrorCategory {

  RECORD(1, "record"),
  CONTROLFIELD(2, "control field"),
  DATAFIELD(3, "data field"),
  INDICATOR(4, "indicator"),
  SUBFIELD(5, "subfield")
  ;

  private final int id;
  private final String name;

  ValidationErrorCategory(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
