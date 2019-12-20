package de.gwdg.metadataqa.marc.cli.parameters;

public enum NetworkAction {
  PRIMARY("primary"),
  PAIRING("pairing")
  ;

  private String label;

  NetworkAction(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
